package com.hamidfarmani.service;

import com.hamidfarmani.config.JwtService;
import com.hamidfarmani.dto.LoginRequest;
import com.hamidfarmani.dto.RegisterRequest;
import com.hamidfarmani.entity.Role;
import com.hamidfarmani.entity.User;
import com.hamidfarmani.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;


  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public String saveUser(RegisterRequest registerRequest) {
    boolean existsByUsername = userRepository.existsByUsername(registerRequest.getUsername());

    if (existsByUsername) {
      throw new RuntimeException("Username already exists");
    }

    User user = User.builder().fullName(registerRequest.getFullName())
        .username(registerRequest.getUsername()).email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword())).role(Role.USER).build();

    userRepository.save(user);

    return "user added to the system";
  }

  public String login(LoginRequest loginRequest) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    if (authenticate.isAuthenticated()) {
      return jwtService.generateToken(loginRequest.getUsername());
    } else {
      throw new RuntimeException("invalid access");
    }

  }

  public void validateToken(String token) {
    jwtService.validateToken(token);
  }


}
