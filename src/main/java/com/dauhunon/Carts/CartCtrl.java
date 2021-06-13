package com.dauhunon.Carts;

import com.dauhunon.Brands.Brand;
import com.dauhunon.Products.ProductService;
import com.dauhunon.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartCtrl {
  private final CartService cartService;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public CartCtrl(CartService cartService, ProductService productService, UserService userService) {
    this.cartService = cartService;
    this.productService = productService;
    this.userService = userService;
  }

  @GetMapping
  public String viewMyCart(Model model) {
    Long userId = userService.getLoggedInUserDetail().getId();
    Cart cart = cartService.getPendingCart(userId);
    model.addAttribute("cart", cart);
    return "cart/cart";
  }

  @GetMapping("/orders")
  public String manageOrders(
    @RequestParam(name = "page", defaultValue = "1") int page,
    Model model
  ) {
    Long userId = userService.getLoggedInUserDetail().getId();
    Page<Cart> pageContent = cartService.listAll(userId, page);
    List<Cart> content = pageContent.getContent();

    int totalPages = pageContent.getTotalPages();
    int totalItems = pageContent.getNumberOfElements();

    model.addAttribute("carts", content);
    model.addAttribute("page", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("totalItems", totalItems);
    return "cart/orders";
  }

  @PostMapping("/checkout")
  public String checkout() {
    Long userId = userService.getLoggedInUserDetail().getId();
    cartService.checkout(userId);
    return "cart/cart";
  }
}
