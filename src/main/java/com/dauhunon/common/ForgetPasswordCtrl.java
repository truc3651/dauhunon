package com.dauhunon.common;

import com.dauhunon.Users.User;
import com.dauhunon.Users.UserService;
import com.dauhunon.utils.App;
import com.dauhunon.utils.Const;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgetPasswordCtrl {
  private UserService userService;
  private JavaMailSender mailSender;

  @Autowired
  public ForgetPasswordCtrl(UserService userService, JavaMailSender mailSender) {
    this.userService = userService;
    this.mailSender = mailSender;
  }

  @GetMapping("forget-password")
  public String viewForgetPasswordPage() {
    return "auth/forget-password";
  }

  @PostMapping("forget-password")
  public RedirectView forgetPassword(
    HttpServletRequest req,
    RedirectAttributes ra,
    @RequestParam("email")String email
  ) {

    try {
      if(!userService.isEmailExisted(email)) {
        ra.addFlashAttribute(App.actionFailed, "The user with this email is non-exists");
        return new RedirectView("/forget-password", true);
      }

      String token = RandomString.make(Const.PASSWORD_TOKEN_LENGTH);
      userService.updateResetPasswordToken(email, token);

      String link = App.getUrlSite(req) + "/reset-password?token=" + token;
      sendMail(email, link);

      ra.addFlashAttribute(App.actionSuccess, "Please check your email");
    } catch (Exception e) {
      System.out.println(e);
      ra.addFlashAttribute(App.actionFailed, "Not able to handle request");
    }
    return new RedirectView("/forget-password", true);
  }

  @GetMapping("reset-password")
  public String viewResetPasswordPage(
    Model model,
    @Param("token")String token
  ) {
    User user = userService.getUserByToken(token);
    if(user == null) {
      model.addAttribute(App.actionFailed, "Token is invalid");
      return "redirect:/reset-password";
    }

    model.addAttribute("token", token);
    return "auth/reset-password";
  }

  @PostMapping("reset-password")
  public RedirectView resetPassword(
    HttpServletRequest req,
    RedirectAttributes ra,
    @RequestParam("token")String token,
    @RequestParam("newPassword")String newPassword
  ) {

    try {
      User user = userService.getUserByToken(token);
      userService.updatePassword(user, newPassword);

      ra.addFlashAttribute(App.actionSuccess, "You've just reset password successfully");
      req.logout();
    } catch (Exception e) {
      ra.addFlashAttribute(App.actionFailed, "Not able to handle request");
    }
    return new RedirectView("/login", true);
  }

  private void sendMail(String email, String link) throws MessagingException, UnsupportedEncodingException {
    MimeMessage mime = mailSender.createMimeMessage();
    MimeMessageHelper mimeHelper = new MimeMessageHelper(mime);

    mimeHelper.setFrom("Đậu hũ non", "FPT Poly");
    mimeHelper.setTo(email);

    String subject = "Here's the link to reset your password";
    String content = "<p>Hello,</p>"
      + "<p>You have requested to reset your password.</p>"
      + "<p>Click the link below to change your password:</p>"
      + "<p><a href=\"" + link + "\">Change my password</a></p>"
      + "<br>"
      + "<p>Ignore this email if you do remember your password, "
      + "or you have not made the request.</p>";

    mimeHelper.setSubject(subject);
    mimeHelper.setText(content, true);

    mailSender.send(mime);
  }
}