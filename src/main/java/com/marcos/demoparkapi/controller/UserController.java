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
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.mapper.UserMapper;
import com.marcos.demoparkapi.service.UserService;

@RestController
@RequestMapping("api/v1/usuarios")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto createDTO) {
		User userSave = userService.save(UserMapper.toUserCreate(createDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(userSave));
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User userById = userService.searchById(id);
		return ResponseEntity.ok(userById);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
		User userUpadtePassword = userService.updatePassword(id, user.getPassword());
		return ResponseEntity.ok(userUpadtePassword);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.finAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

}
