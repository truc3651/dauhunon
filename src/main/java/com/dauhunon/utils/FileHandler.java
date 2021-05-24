package com.dauhunon.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {
  public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
    Path uploadPath = Paths.get(uploadDir);

    if(!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String getPhotosName(
    MultipartFile multipartFile,
    String prefixFileName) {

    String originalFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    String splitFileName[] = originalFilename.split("\\.");
    String typeOfPhoto = splitFileName[splitFileName.length - 1];

    String fileName = prefixFileName + "." + typeOfPhoto;

    return fileName;
  }

  public static void deleteFile(String pathFile) {
    String workingDir = Paths.get("")
      .toAbsolutePath()
      .toString();
    workingDir = workingDir.replace("\\", "/");

    File file = new File(workingDir + pathFile);
    file.delete();
  }
}
