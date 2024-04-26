package com.marcos.demoparkapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.dto.ClienteCreateDto;
import com.marcos.demoparkapi.dto.ClienteResponseDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.entities.Cliente;
import com.marcos.demoparkapi.exceptions.ErrorMessage;
import com.marcos.demoparkapi.jwt.JwtUserDetails;
import com.marcos.demoparkapi.mapper.ClienteMapper;
import com.marcos.demoparkapi.service.ClienteService;
import com.marcos.demoparkapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Customers", description = "Contains all features for the customer")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
	
	@Autowired 
	private  ClienteService customerService;
	@Autowired
	 private  UserService userService;

	    @Operation(summary = "Create new customer", description="Resource create new customer",
	            responses = {
	                    @ApiResponse(responseCode = "201",
	                            description = "Resource to successfully create customer.",
	                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
	                    @ApiResponse(responseCode = "409",
	                            description = "CPF already exists in the database.",
	                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
	                    @ApiResponse(responseCode = "422",
	                            description = "Resource not processed due to invalid data entry.",
	                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
	                    @ApiResponse(responseCode = "403",
	                            description = "Resource not allowed for ADMIN profile",
	                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	            })
	    @PostMapping
	    @PreAuthorize("hasRole('CLIENTE')")
	    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid
	                                                      ClienteCreateDto customerCreateDTO,
	                                                      @AuthenticationPrincipal JwtUserDetails userDetails){
	        Cliente customer = ClienteMapper.toCustomertCreate(customerCreateDTO);
	        customer.setUser(userService.searchById(userDetails.getId()));
	        customerService.save(customer);
	        return ResponseEntity.status(201).body(ClienteMapper.toClientResponse(customer));
	    }

}
