package com.sigl.sigl.controller;
import com.sigl.sigl.dto.LoginForm;
import com.sigl.sigl.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<Object> authenticateUser(@RequestBody LoginForm loginForm) {
    return this.authService.authenticateUser(loginForm);
  }


}
