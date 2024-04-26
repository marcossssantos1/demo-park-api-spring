package com.marcos.demoparkapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.dto.UserAuthDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.exceptions.ErrorMessage;
import com.marcos.demoparkapi.jwt.JwtToken;
import com.marcos.demoparkapi.jwt.JwtUserDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "Authetication", description = "The Api is auth to user authetication with token JWT")
public class AuthenticationController {
	
	private  JwtUserDetailsService jwtUserDetailsService;
    private  AuthenticationManager authenticationManager;
    

    @Operation(summary = "Auth user", description="Resource auth user",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Resource to successfully auth user with token in bearer.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid credential.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422",
                            description = "invalid fields.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
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
