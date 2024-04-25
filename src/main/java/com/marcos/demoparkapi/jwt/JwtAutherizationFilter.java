package com.marcos.demoparkapi.jwt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAutherizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    private void toAuthentication(HttpServletRequest httpServletRequest, String username) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

        final String token = httpServletRequest.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if (token == null  || !token.startsWith(JwtUtils.JWT_BEARER)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (!JwtUtils.isTokenValid(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String username = JwtUtils.getUsernameFromToken(token);

        toAuthentication(httpServletRequest, username);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
