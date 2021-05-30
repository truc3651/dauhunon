package com.dauhunon.common;

import com.dauhunon.Users.User;
import com.dauhunon.Users.UserService;
import com.dauhunon.utils.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthCtrl {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AuthCtrl(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("login")
  public String viewLoginPage() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth == null || auth instanceof AnonymousAuthenticationToken)
      return "auth/login";

    return "redirect:/";
  }

  @GetMapping("register")
  public String viewRegisterPage(
    Model model
  ) {
    model.addAttribute("user", new User());
    return "auth/register";
  }

  @PostMapping("/register")
  public RedirectView register(
    @ModelAttribute("user") User user,
    RedirectAttributes ra
  ) {
    if(userService.getUserByUsername(user.getUsername()) != null) {
      ra.addFlashAttribute(App.actionFailed, "Username provided is already existed");
      ra.addFlashAttribute("user", user);

      return new RedirectView("/register", true);
    }
    else if(userService.isEmailExisted(user.getEmail())) {
      ra.addFlashAttribute(App.actionFailed, "Email provided is already existed");
      ra.addFlashAttribute("user", user);

      return new RedirectView("/register", true);
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.save(user);

    ra.addFlashAttribute(App.actionSuccess, "Your account is ready to GO. Now you need to login!");
    return new RedirectView("/login", true);
  }
}
