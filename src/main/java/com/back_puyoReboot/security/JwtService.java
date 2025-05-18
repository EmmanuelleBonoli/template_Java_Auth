package com.back_puyoReboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${security.jwt.secret-key}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .claim("roles", userDetails.getAuthorities())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
      .compact();
  }

  public Claims extractClaims(String token) {
    return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
  }

  public String extractEmail(String token) {
    return extractClaims(token).getSubject();
  }

  public boolean validateJwtToken(String token, HttpServletResponse response) {
    try {
      Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      sendErrorResponse(response, "Token expired");
      return false;
    } catch (JwtException | IllegalArgumentException e) {
      System.out.println(e.getMessage());
      sendErrorResponse(response, "Invalid or expired token");
      return false;
    }
  }

  private void sendErrorResponse(HttpServletResponse response, String message) {
    try {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("status", HttpStatus.UNAUTHORIZED.toString());
      errorResponse.put("message", message);
      errorResponse.put("type", "JWT Error");

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      ObjectMapper mapper = new ObjectMapper();
      response.getWriter().write(mapper.writeValueAsString(errorResponse));
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException ioException) {
      throw new RuntimeException("Failed to write response", ioException);
    }
  }
}
