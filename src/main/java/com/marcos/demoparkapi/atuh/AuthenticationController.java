package com.marcos.demoparkapi.atuh;

import org.springframework.security.core.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.marcos.demoparkapi.dto.UserAuthDto;
import com.marcos.demoparkapi.exceptions.ErrorMessage;
import com.marcos.demoparkapi.jwt.JwtToken;
import com.marcos.demoparkapi.jwt.JwtUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public class AuthenticationController {
	
	private  JwtUserDetailsService jwtUserDetailsService;
    private  AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody @Valid UserAuthDto userAuthDto, HttpServletRequest httpServletRequest){
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(userAuthDto.getUsername());
            return ResponseEntity.ok().body(token);
        }catch (AuthenticationException error){
        }
        return ResponseEntity.badRequest().body( new ErrorMessage(httpServletRequest, HttpStatus.BAD_REQUEST, "Invalid credential"));
    }

}
