package com.dauhunon.common;

import com.dauhunon.Brands.BrandService;
import com.dauhunon.Products.Product;
import com.dauhunon.Products.ProductService;
import com.dauhunon.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeCtrl {
  private final ProductService productService;
  private final BrandService brandService;

  @Autowired
  public HomeCtrl(ProductService productService, BrandService brandService, UserService userService, PasswordEncoder passwordEncoder) {
    this.productService = productService;
    this.brandService = brandService;
  }

  @GetMapping
  public String viewHomePage(Model model) {
    List<Product> listProducts = productService.listAll();
    model.addAttribute("listProducts", listProducts);
    return "index";
  }
}
