package com.back_puyoReboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException {
    String[] exceptionClassParts = authException.getClass().getName().split("\\.");
    String exceptionType = exceptionClassParts[exceptionClassParts.length - 1];
    System.out.println(exceptionType);
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("status", HttpStatus.UNAUTHORIZED.toString());
    errorResponse.put("message", "Authentication failed");
    errorResponse.put("type", exceptionType);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    OutputStream responseStream = response.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(responseStream, errorResponse);

    responseStream.flush();
  }
}
