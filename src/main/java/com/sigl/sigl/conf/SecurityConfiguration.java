package com.sigl.sigl.conf;

import com.sigl.sigl.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

  @Autowired JwtUtils jwtUtils;

  @Value( "${project.security}" )
  private String security;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    if(!"dev".equals(security)){
    JwtTokenFilter customFilter = new JwtTokenFilter(this.jwtUtils);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    http.authorizeRequests(authorizeRequests -> authorizeRequests.requestMatchers("login").permitAll()
              .anyRequest().authenticated())
          .csrf((csrf) -> csrf.disable());
    }else{
      http.authorizeRequests(authorizeRequests -> authorizeRequests.requestMatchers("**").permitAll()
              .anyRequest().authenticated())
          .csrf((csrf) -> csrf.disable());
    }
    return http.build();
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .build();
  }



}