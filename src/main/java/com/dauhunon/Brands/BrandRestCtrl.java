package com.dauhunon.Brands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestCtrl {
  private final BrandService brandService;

  @Autowired
  public BrandRestCtrl(BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping
  public List<Brand> getBrands() {
    List<Brand> brands = brandService.listAll();
    return brands;
  }
}
