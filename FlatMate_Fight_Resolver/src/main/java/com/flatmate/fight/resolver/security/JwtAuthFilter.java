package com.flatmate.fight.resolver.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	private final JwtUtil jwtUtil;
	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil=jwtUtil;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		String token = authHeader.substring(7);
		try {
            String username = jwtUtil.extractUsername(token);
            if (!jwtUtil.validateToken(token, username)) {
                filterChain.doFilter(request, response);
                return;
            }

            User principal = new User(username, "", new java.util.ArrayList<>());
            var authToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                    principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (ExpiredJwtException e) {

            System.out.println("JWT expired: " + e.getMessage());
        } catch (Exception e) {

            System.out.println("JWT invalid: " + e.getMessage());
        }
		filterChain.doFilter(request, response);
	}
}
