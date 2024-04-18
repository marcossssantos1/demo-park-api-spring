package com.marcos.demoparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.demoparkapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

}
