package com.dauhunon.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.EagerTransformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileHandler {

  public static final Cloudinary cloudinary = new Cloudinary(Const.CLOUDINARY);

  public static Map uploadFile(MultipartFile multipartFile, String subFolderName, String fileName) throws IOException {
    Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap(
      "moderation", "manual",
      "public_id", App.appName + "/" + subFolderName + "/" + fileName,
      "eager", Arrays.asList(
        new EagerTransformation().width(400).height(300).crop("pad")
      ))
    );
    return uploadResult;
  }

  public static void deleteFile(String publicId) throws IOException {
    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
  }
}
