package com.back_puyoReboot.Authentication;

import com.back_puyoReboot.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(JwtService jwtService, AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public String authenticate(String email, String password) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return jwtService.generateToken((UserDetails) authentication.getPrincipal());
  }
}
