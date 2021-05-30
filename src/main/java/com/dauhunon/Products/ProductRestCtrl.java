package com.dauhunon.Products;

import com.cloudinary.Cloudinary;
import com.cloudinary.EagerTransformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestCtrl {
  private final ProductService productService;

  public ProductRestCtrl(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> listAll() {
    return productService.listAll();
  }

  @PostMapping("uploadtest")
  public Map upload() throws IOException {
    Map config = new HashMap();
    config.put("cloud_name", "dwnj15uud");
    config.put("api_key", "221752539721262");
    config.put("api_secret", "8xQfarN3gHbWi0deJavzjZ7GzKA");
    Cloudinary cloudinary = new Cloudinary(config);

    String workingDir = Paths.get("")
      .toAbsolutePath()
      .toString();
    workingDir = workingDir.replace("\\", "/");
    File file = new File(workingDir + "/photos/brands/1.jpg");

    UUID uuid = UUID.randomUUID();

    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap(
      "moderation", "manual",
      "public_id", "dauhunon/products/" + uuid.toString(),
      "eager", Arrays.asList(
        new EagerTransformation().width(400).height(300).crop("pad")
      ))
    );

    return uploadResult;
  }
}
