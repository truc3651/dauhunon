package com.dauhunon.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/user/")
public class UserRestCtrl {
  private UserService userService;

  @Autowired
  public UserRestCtrl(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("tructest")
  public User tructest() {
    User user = new User();

    try {
      System.out.println(">>test");
      userService.save(user);
    } catch (Exception e) {
      System.out.println(">>test error");
    }
    return user;
  }
}
