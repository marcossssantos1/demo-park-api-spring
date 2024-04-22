package com.marcos.demoparkapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.dto.UserCreateDto;
import com.marcos.demoparkapi.dto.UserPasswordDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.exceptions.ErrorMessage;
import com.marcos.demoparkapi.mapper.UserMapper;
import com.marcos.demoparkapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuarios", description = "Contem todas as operações relativas aos recursos para cadastro,, edição e leitura de um usuario")
@RestController
@RequestMapping("api/v1/usuarios")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Criar um novo usuario", description="Recurso para criar um novo usuario", responses = {
			@ApiResponse(responseCode = "201",
							description = "Usuario criado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
			@ApiResponse(responseCode = "409",
					description = "E-mail já existe na base de dados",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422",
					description = "Dados de entrada estão invalidos",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	@PostMapping
	public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDTO) {
		User userSave = userService.save(UserMapper.toUserCreate(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userSave));
	}
	

	@Operation(summary = "Recuperar usuario pelo id", description="Recurso para buscar um usuario pelo id", responses = {
			@ApiResponse(responseCode = "200",
							description = "Usuario recuperado com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
			@ApiResponse(responseCode = "404",
					description = "Recurso não encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
		User userById = userService.searchById(id);
		return ResponseEntity.ok(UserMapper.toUserResponse(userById));
	}

	@Operation(summary = "Atualizar a senha do usuario", description="Recurso para buscar um usuario pelo id e atualizar a senha", responses = {
			@ApiResponse(responseCode = "204",
							description = "Senha atualizada com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
			@ApiResponse(responseCode = "400",
					description = "Senha não confere",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404",
			description = "Recurso não encontrado",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
	})
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto passwordDTO){
		userService.updatePassword(id, passwordDTO.getPassword(), passwordDTO.getNewPassword(), passwordDTO.getConfirmPassword());
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Recuperar todos os usuarios", description="Recurso para buscar todos os usuario na base de dados", responses = {
			@ApiResponse(responseCode = "200",
							description = "Todos os usuarios foram recuperados com sucesso",
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
	})
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.finAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
}
