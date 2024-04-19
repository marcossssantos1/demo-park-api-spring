package com.marcos.demoparkapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	public User searchById(Long id) {
	return userRepository.findById(id).orElseThrow( 
			() -> new RuntimeException("Usuario não encontrado")
			);
	}

}
