package com.back_puyoReboot.security;

import static com.back_puyoReboot.security.SecurityConstants.PUBLIC_URLS;

import com.back_puyoReboot.User.CustomUserDetailsService;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

  @Value("${cors.allowedOrigins}")
  private String allowedOrigins;

  private final CustomUserDetailsService customUserDetailsService;
  private final CustomAuthEntryPoint customAuthEntryPoint;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(
    CustomUserDetailsService customUserDetailsService,
    CustomAuthEntryPoint customAuthEntryPoint,
    JwtAuthenticationFilter jwtAuthenticationFilter
  ) {
    this.customUserDetailsService = customUserDetailsService;
    this.customAuthEntryPoint = customAuthEntryPoint;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS.toArray(new String[0])).permitAll().anyRequest().authenticated())
      .userDetailsService(customUserDetailsService)
      .exceptionHandling(e -> e.authenticationEntryPoint(customAuthEntryPoint))
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList(allowedOrigins));
    configuration.addAllowedHeader("Content-Type");
    configuration.addAllowedHeader("Authorization");
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "HEAD", "OPTIONS"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
