package com.dauhunon.Users;

import com.dauhunon.auth.MyUsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private UserRepo userRepo;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }

  public User getUserByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  public User getUserByEmail(String email) {
    return userRepo.findByEmail(email);
  }

  public boolean isAuthenticated() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth != null || !(auth instanceof AnonymousAuthenticationToken);
  }

  public User getLoggedInUserDetail() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    MyUsersDetails userDetail = (MyUsersDetails) auth.getPrincipal();

    return userDetail.getUser();
  }

  public void setLoggedInUserDetail(User user) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    MyUsersDetails userDetail = (MyUsersDetails) auth.getPrincipal();

    userDetail.setUser(user);
  }

  public void save(User user) {
    userRepo.save(user);
  }

  public void assignImage(Long id, String imageUrl) {
    User user = userRepo.findById(id).orElseThrow(() -> new IllegalStateException("Non exists"));
    user.setAvatarUrl(imageUrl);

    userRepo.save(user);
  }

  public boolean isEmailExisted(String email) {
    User user = userRepo.findByEmail(email);
    return user != null ? true : false;
  }

  public User updateResetPasswordToken(String email, String token) {
    User user = userRepo.findByEmail(email);
    if(user != null) {
      user.setResetPasswordToken(token);
      userRepo.save(user);

      return user;
    } else {
      throw new IllegalStateException("Non email exists");
    }
  }

  public User getUserByToken(String token) {
    return userRepo.findByPasswordToken(token);
  }

  public void updatePassword(User user, String newPwd) {
    user.setPassword(passwordEncoder.encode(newPwd));
    user.setResetPasswordToken(null);

    userRepo.save(user);
  }
}
