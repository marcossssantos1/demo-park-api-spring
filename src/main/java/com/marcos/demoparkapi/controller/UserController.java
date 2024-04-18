package com.marcos.demoparkapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.service.UserService;

@RestController
@RequestMapping("api/v1/usuarios")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> create(@RequestBody User user){
		User userSave = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}

}
