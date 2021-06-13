package com.dauhunon.Users;

import com.dauhunon.utils.App;
import com.dauhunon.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class UserCtrl {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserCtrl(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("profile")
  public String viewProfilePage(
    Model model) {

    model.addAttribute("user", userService.getLoggedInUserDetail());
    return "users/profile";
  }

  @PostMapping("update-profile")
  public RedirectView editProfile(
    RedirectAttributes ra,
    @ModelAttribute("user") User editedLoggedInUser,
    @RequestParam("image") MultipartFile multipartFile
  ) throws IOException {
    User loggedInUser = userService.getLoggedInUserDetail();

    if(!passwordEncoder.matches(editedLoggedInUser.getPassword(), loggedInUser.getPassword())){
      ra.addFlashAttribute(App.actionFailed, "Password is not correct");
      ra.addFlashAttribute("user", editedLoggedInUser);
      return new RedirectView("/profile", true);
    }

    User userByEmail = userService.getUserByEmail(editedLoggedInUser.getEmail());
    if(userByEmail != null && !userByEmail.getId().equals(loggedInUser.getId())) {
      ra.addFlashAttribute(App.actionFailed, "Email provided is already in use");
      ra.addFlashAttribute("user", editedLoggedInUser);
      return new RedirectView("/profile", true);
    }

    loggedInUser.setFullName(editedLoggedInUser.getFullName());
    loggedInUser.setEmail(editedLoggedInUser.getEmail());
    loggedInUser.setUsername(editedLoggedInUser.getUsername());
    userService.save(loggedInUser);

    if(!multipartFile.isEmpty()) {
      FileHandler.deleteFile(editedLoggedInUser.getPublicId());

      Map uploadResult = FileHandler.uploadFile(multipartFile, "users", editedLoggedInUser.getId()+"");
      String secureUrl = (String) uploadResult.get("secure_url");

      userService.assignImage(editedLoggedInUser.getId(), secureUrl);
      loggedInUser.setAvatarUrl(secureUrl);
    }
    userService.setLoggedInUserDetail(loggedInUser);

    ra.addFlashAttribute(App.actionSuccess, "Updated profile success");
    return new RedirectView("/profile", true);
  }

  @GetMapping("/change-password")
  public String modifyPassword(
    Model model
  ) {
    model.addAttribute("user", userService.getLoggedInUserDetail());
    return "users/change-password";
  }

  @PostMapping("/change-password")
  public RedirectView changePassword(
    @RequestParam("password") String password,
    @RequestParam("newPassword") String newPassword,
    HttpServletRequest req,
    RedirectAttributes ra
  ) throws ServletException {

    User loggedInUser = userService.getLoggedInUserDetail();

    if(!passwordEncoder.matches(password, loggedInUser.getPassword())) {
      ra.addFlashAttribute(App.actionFailed, "Password is not correct");
      return new RedirectView("/change-password", true);
    }

    loggedInUser.setPassword(passwordEncoder.encode(newPassword));
    userService.save(loggedInUser);

    req.logout();
    ra.addFlashAttribute(App.actionSuccess, "You've just changed your password success");

    return new RedirectView("/login", true);
  }
}
