package com.dauhunon.auth;

import com.dauhunon.Users.User;
import com.dauhunon.Users.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepo userRepo;

  @Autowired
  public UserDetailsServiceImpl(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Could not find user");
    }
    return new MyUsersDetails(user);
  }

}
