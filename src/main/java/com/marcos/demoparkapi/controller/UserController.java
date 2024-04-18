package com.marcos.demoparkapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.demoparkapi.service.UserService;

@RestController
@RequestMapping("api/v1/usuarios")
public class UserController {
	
	@Autowired
	private UserService userService;

}
