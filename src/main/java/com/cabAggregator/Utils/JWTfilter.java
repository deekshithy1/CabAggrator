package com.cabAggregator.Utils;

import com.cabAggregator.DTO.UserDetailsDTO;
import com.cabAggregator.Service.JWTservice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTfilter extends OncePerRequestFilter {

    private final JWTservice jwtService;

    // List of endpoints to skip JWT validation
    private static final List<String> PUBLIC_URLS = List.of(
            "/login",
            "/register",
            "/captain-register",
            "/captain-login",
            "/Get-allcaptains"
    );

    public JWTfilter(JWTservice jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT filter for public endpoints
        if (PUBLIC_URLS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Extract user details (works for both User and Captain)
            UserDetailsDTO userDetails = jwtService.extractUserDetails(token);

            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
