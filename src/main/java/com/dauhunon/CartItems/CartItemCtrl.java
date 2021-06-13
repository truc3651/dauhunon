package com.dauhunon.CartItems;

import com.dauhunon.Products.ProductService;
import com.dauhunon.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cart")
public class CartItemCtrl {
  private final CartItemService cartItemService;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public CartItemCtrl(CartItemService cartItemService, ProductService productService, UserService userService) {
    this.cartItemService = cartItemService;
    this.productService = productService;
    this.userService = userService;
  }

  @PostMapping("/add")
  public String addCartItem(
    @RequestParam long id,
    @RequestParam(required = false, defaultValue = "1") int total,
    HttpServletRequest request
  ) {
    cartItemService.addCartItem(userService.getLoggedInUserDetail(), id, total);
    String referer = request.getHeader("Referer");
    return "redirect:"+ referer;
  }

  @PostMapping("/remove")
  public String removeCartItem(
    @RequestParam long id,
    @RequestParam(required = false) boolean isAll,
    HttpServletRequest request
  ) {
    cartItemService.removeCartItem(id, isAll);
    String referer = request.getHeader("Referer");
    return "redirect:"+ referer;
  }


}
