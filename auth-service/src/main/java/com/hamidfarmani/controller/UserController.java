package com.hamidfarmani.controller;

import com.hamidfarmani.dto.LoginRequest;
import com.hamidfarmani.dto.RegisterRequest;
import com.hamidfarmani.entity.User;
import com.hamidfarmani.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public String addNewUser(@RequestBody RegisterRequest registerRequest) {
    return userService.saveUser(registerRequest);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

  @GetMapping("/validate")
  public String validateToken(@RequestParam("token") String token) {
    userService.validateToken(token);
    return "Token is valid";
  }
}
