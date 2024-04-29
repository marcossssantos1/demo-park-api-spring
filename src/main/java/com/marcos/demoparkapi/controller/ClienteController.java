package com.marcos.demoparkapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.dto.ClienteCreateDto;
import com.marcos.demoparkapi.dto.ClienteResponseDto;
import com.marcos.demoparkapi.dto.PagebleDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.entities.Cliente;
import com.marcos.demoparkapi.exceptions.ErrorMessage;
import com.marcos.demoparkapi.jwt.JwtUserDetails;
import com.marcos.demoparkapi.mapper.ClienteMapper;
import com.marcos.demoparkapi.mapper.PageableMapper;
import com.marcos.demoparkapi.repository.projection.ClienteProjection;
import com.marcos.demoparkapi.service.ClienteService;
import com.marcos.demoparkapi.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Customers", description = "Contains all features for the customer")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	@Autowired
	private ClienteService customerService;
	@Autowired
	private UserService userService;

	@Operation(summary = "Create new customer", description = "Resource create new customer", responses = {
			@ApiResponse(responseCode = "201", description = "Resource to successfully create customer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
			@ApiResponse(responseCode = "409", description = "CPF already exists in the database.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "422", description = "Resource not processed due to invalid data entry.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "403", description = "Resource not allowed for ADMIN profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))) })
	@PostMapping
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto customerCreateDTO,
			@AuthenticationPrincipal JwtUserDetails userDetails) {
		Cliente customer = ClienteMapper.toCustomertCreate(customerCreateDTO);
		customer.setUser(userService.searchById(userDetails.getId()));
		customerService.save(customer);
		return ResponseEntity.status(201).body(ClienteMapper.toClientResponse(customer));
	}

	@Operation(summary = "Localizar um cliente", description = "Recurso para localizar um cliente pelo ID. "
			+ "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'", security = @SecurityRequirement(name = "security"), responses = {
					@ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
					@ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id) {
		Cliente cliente = customerService.buscarPorId(id);
		return ResponseEntity.ok(ClienteMapper.toClientResponse(cliente));
	}

	@Operation(summary = "Recuperar lista de clientes", description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ", security = @SecurityRequirement(name = "security"), parameters = {
			@Parameter(in = QUERY, name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")), description = "Representa a página retornada"),
			@Parameter(in = QUERY, name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "5")), description = "Representa o total de elementos por página"),
			@Parameter(in = QUERY, name = "sort", hidden = true, array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")), description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
					@ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PagebleDto> getAll(
			@Parameter(hidden = true) @PageableDefault(size = 5, sort = { "nome" }) Pageable pageable) {
		Page<ClienteProjection> clientes = customerService.buscarTodos(pageable);
		return ResponseEntity.ok(PageableMapper.toDto(clientes));
	}

	@Operation(summary = "Recuperar dados do cliente autenticado", description = "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'", security = @SecurityRequirement(name = "security"), responses = {
			@ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
			@ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de ADMIN", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))) })
	@GetMapping("/detalhes")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails) {
		Cliente cliente = customerService.buscarPorUsuarioId(userDetails.getId());
		return ResponseEntity.ok(ClienteMapper.toClientResponse(cliente));
	}
}
