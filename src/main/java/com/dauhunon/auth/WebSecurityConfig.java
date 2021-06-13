package com.dauhunon.auth;

import com.dauhunon.Users.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserRepo userRepo;

  public WebSecurityConfig(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(this.userRepo);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/js/**", "/css/**", "/img/**", "/photos/**").permitAll()
      .antMatchers("/forget-password", "/reset-password/**",  "/register/**", "/login/**", "/api/**").not().authenticated()
      .anyRequest().authenticated()
      .and()
      .formLogin().permitAll()
      .loginPage("/login")
      .defaultSuccessUrl("/")
      .and()
      .logout().permitAll()
      .and()
      .rememberMe()
      .key("abcdef123456")
      .tokenValiditySeconds(30 * 24 * 60 * 60)
      .and()
      .csrf().disable()
    ;
    http.headers().frameOptions().disable();
  }
}