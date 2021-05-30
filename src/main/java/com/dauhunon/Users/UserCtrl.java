package com.dauhunon.Users;

import com.dauhunon.auth.MyUsersDetails;
import com.dauhunon.utils.App;
import com.dauhunon.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    Model model,
    @AuthenticationPrincipal MyUsersDetails userDetail) {

    User loggedInUser = userDetail.getUser();
    if(loggedInUser == null) return "auth/login";

    model.addAttribute("user", loggedInUser);
    return "users/profile";
  }

  @PostMapping("update-profile")
  public RedirectView editProfile(
    RedirectAttributes ra,
    @ModelAttribute("user") User editedLoggedInUser,
    @RequestParam("image") MultipartFile multipartFile,
    @AuthenticationPrincipal MyUsersDetails userDetail
  ) throws IOException {
    User loggedInUser = userDetail.getUser();

    if(!passwordEncoder.matches(editedLoggedInUser.getPassword(), loggedInUser.getPassword())){
      ra.addFlashAttribute(App.actionFailed, "Password is not correct");
      ra.addFlashAttribute("user", editedLoggedInUser);

      return new RedirectView("/profile", true);
    }

    if(userService.isEmailExisted(editedLoggedInUser.getEmail())) {
      ra.addFlashAttribute(App.actionFailed, "Email provided is already in use");

      return new RedirectView("/profile", true);
    }

    editedLoggedInUser.setPassword(passwordEncoder.encode(editedLoggedInUser.getPassword()));
    userService.save(editedLoggedInUser);

    if(!multipartFile.isEmpty()) {
      FileHandler.deleteFile(editedLoggedInUser.getPublicId());

      Map uploadResult = FileHandler.uploadFile(multipartFile, "users", editedLoggedInUser.getId()+"");
      String secureUrl = (String) uploadResult.get("secure_url");

      userService.assignImage(editedLoggedInUser.getId(), secureUrl);
    }
    userDetail.setUser(editedLoggedInUser);

    ra.addFlashAttribute(App.actionSuccess, "Updated profile success");
    return new RedirectView("/profile", true);
  }

  @GetMapping("/change-password")
  public String modifyPassword(
    Model model,
    @AuthenticationPrincipal MyUsersDetails userDetails) {

    model.addAttribute("user", userDetails.getUser());
    return "users/change-password";
  }

  @PostMapping("/change-password")
  public RedirectView changePassword(
    HttpServletRequest req,
    @AuthenticationPrincipal MyUsersDetails userDetail,
    RedirectAttributes ra) throws ServletException {

    String newPwd = req.getParameter("newPwd");
    String oldPwd = req.getParameter("oldPwd");

    User loggedInUser = userDetail.getUser();

    if(!passwordEncoder.matches(oldPwd, loggedInUser.getPassword())) {
      ra.addFlashAttribute(App.actionFailed, "Old password is incorrect");
      return new RedirectView("/change-password", true);
    }

    loggedInUser.setPassword(newPwd);
    loggedInUser.setPassword(passwordEncoder.encode(loggedInUser.getPassword()));
    userService.save(loggedInUser);

    req.logout();
    ra.addFlashAttribute(App.actionSuccess, "You've just changed your password success");
    return new RedirectView("/login", true);
  }
}
