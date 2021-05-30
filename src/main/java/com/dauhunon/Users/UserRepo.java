package com.dauhunon.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
  @Query("select u from User u where u.email = ?1")
  public User findByEmail(String email);

  @Query("select u from User u where u.username = ?1")
  public User findByUsername(String username);

  @Query("select u from User u where u.resetPasswordToken = ?1")
  public User findByPasswordToken(String resetPasswordToken);
}
