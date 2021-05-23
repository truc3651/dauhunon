package com.dauhunon.common;

import com.dauhunon.Brands.Brand;
import com.dauhunon.Brands.BrandRepo;
import com.dauhunon.Products.Product;
import com.dauhunon.Products.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class Config {
  @Bean
  CommandLineRunner commandLineRunner(BrandRepo brandRepo, ProductRepo productRepo) {
    return args -> {
//      Brand apple = new Brand("Apple", 5, "Apple xin xo", "");
//      Brand samsung = new Brand("Samsung", 5, "Samsung xin xo", "");
//
//      brandRepo.saveAll(List.of(apple, samsung));

//      Product product = new Product(apple, "iphone", "", "", 120000F, "", 5);
//
//      productRepo.saveAll(List.of(product));
    };
  }
}
