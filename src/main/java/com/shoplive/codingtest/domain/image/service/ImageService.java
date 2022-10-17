package com.shoplive.codingtest.domain.image.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.shoplive.codingtest.domain.image.domain.entity.Image;
import com.shoplive.codingtest.domain.image.domain.repository.ImageRepository;
import com.shoplive.codingtest.domain.image.exception.FailLocalFileDeleted;
import com.shoplive.codingtest.domain.image.exception.FailedUploadImageToLocalException;
import com.shoplive.codingtest.domain.image.exception.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

  private final AmazonS3Client amazonS3Client;

  private final ImageRepository imageRepository;

  @Value("${cloud.aws.s3.bucket}")
  public String bucket;

  public List<Image> saveAllImage(List<Image> imageList) {
    return imageRepository.saveAll(imageList);
  }

  /**
   * 이미지를 업로드 할 때 꼭 로컬에 한번 저장해야할까? 대부분의 예제들이 local에 한번 저장한 후 cloud storage에 올리는데 이유는 나와 있지 않았습니다.
   * 이번에 구현해보면서 spring Docs에 MultiPartFile class 내용을 보니 request가 끝나면 파일이 사라지니 저장해둘 의무가 있다고 나옵니다. 따라서
   * 실패하면 이미지가 사라지니 로컬에 한번 저장한 후 클라우드에 올리는 것이 좋다고 생각합니다. <a
   * href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html">...</a>
   * TODO 용량 검사 5MB
   *
   * @param multipartFile request로 받은 이미지
   * @return image entity 반환 boardId는 별도 추가 필요
   */
  public Image uploadToS3(MultipartFile multipartFile) {
    String newUUID = UUID.randomUUID().toString();

    // 1. 로컬에 업로드 한다.
    File localFile =
        localUpload(multipartFile, newUUID).orElseThrow(FailedUploadImageToLocalException::new);
    String newImageName = newUUID + "_" + multipartFile.getOriginalFilename();
    // 2. s3에 이미지를 업로드한다.
    String cloudPath = uploadToS3(newImageName, localFile);
    // 3. local에 이미지를 지운다.
    cleanUpLocalFile(localFile);
    return Image.builder().id(newUUID).name(newImageName).cloudPath(cloudPath).build();
  }

  /**
   * @param fileName 파일 이름
   * @param localFile 업로드될 파일
   * @return cloudPath - image url 반환
   */
  private String uploadToS3(String fileName, File localFile) {
    log.info("2234344334343" + localFile.getPath());

    amazonS3Client.putObject(
        new PutObjectRequest(bucket, fileName, localFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  public Optional<File> localUpload(MultipartFile file, String newUUID) {
    log.info(file.getOriginalFilename() + "-------------");
    File newLocalFile =
        new File(
            System.getProperty("user.dir")
                + "\\image\\"
                + newUUID
                + "_"
                + file.getOriginalFilename());
    log.info(newLocalFile.getName() + "-------------");
    log.info(newLocalFile.getPath() + "-------------");

    try {
      if (newLocalFile.createNewFile()) {
        try (FileOutputStream fos = new FileOutputStream(newLocalFile)) {
          fos.write(file.getBytes());
        }
        return Optional.of(newLocalFile);
      }
    } catch (IOException e) {
      log.warn("local upload fail");
      throw new FailedUploadImageToLocalException();
    }
    return Optional.empty();
  }

  public void deleteS3Image(String fileName) {
    amazonS3Client.deleteObject(bucket, fileName);
  }

  public void deleteAllImage(List<Image> imageList) {
    imageRepository.deleteAllInBatch(imageList);
  }

  public void cleanUpLocalFile(File file) {

    try {
      Files.delete(file.toPath());
    } catch (IOException e) {
      throw new FailLocalFileDeleted();
    }
  }

  public List<Image> findImageByBoardId(Long boardId) {
    return imageRepository.findAllByBoardId(boardId).orElseThrow(ImageNotFoundException::new);
  }
}
