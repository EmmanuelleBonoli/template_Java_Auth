package com.back_puyoReboot.security;

import static com.back_puyoReboot.security.SecurityConstants.PUBLIC_URLS;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isPublicUrl(request) && parseJwt(request) == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authenticateRequest(request, response)) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean authenticateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwt = parseJwt(request);
        if (jwt == null) return true;

        if (!jwtService.validateJwtToken(jwt, response)) {
            sendUnauthorizedResponse(response);
            return false;
        }

        return true;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Token invalide - Non Authorisé\", \"error\": \"INVALID_TOKEN\"}");
        response.getWriter().flush();
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String bearer = "Bearer ";
        if (headerAuth != null && headerAuth.startsWith(bearer)) {
            return headerAuth.substring(bearer.length());
        }
        return null;
    }

    private boolean isPublicUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return PUBLIC_URLS.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

}
