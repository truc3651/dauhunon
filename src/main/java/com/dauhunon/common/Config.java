package com.dauhunon.common;

import com.dauhunon.Brands.Brand;
import com.dauhunon.Brands.BrandRepo;
//import com.dauhunon.Carts.Cart;
//import com.dauhunon.Carts.CartRepo;
import com.dauhunon.Products.Product;
import com.dauhunon.Products.ProductRepo;
//import com.dauhunon.Users.User;
//import com.dauhunon.Users.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class Config {
//  @Bean
//  CommandLineRunner commandLineRunner(
//    BrandRepo brandRepo,
//    ProductRepo productRepo
//    CartRepo cartRepo,
//    UserRepo userRepo
//  ) {
//    return args -> {
//      User user = new User("thanhtre3651@gmail.com", "trucstre", "Nguyen Truc", "0348401045", "");
//      userRepo.save(user);
//
//      Brand apple = new Brand("Apple", 5, "Apple xin xo", true);
//      brandRepo.save(apple);
//
//      Product product = new Product("iphone", "iphone", "", 120000F, 5, 10F, true);
//      product.setBrand(apple);
//      productRepo.saveAll(List.of(product));
//
//      Cart cart = new Cart(10000F, 20F);
//      cart.addProduct(product);
//      cart.setUser(user);
//      cartRepo.save(cart);
//
//      List<Product> products = brandRepo.listAllProduct();
//      System.out.println(">>size: " + products.size());
//    };
//  }
}
