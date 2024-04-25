package com.marcos.demoparkapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.exceptions.PasswordInvalidException;
import com.marcos.demoparkapi.exceptions.UserIdEntityNotFoundException;
import com.marcos.demoparkapi.exceptions.UsernameUniqueViolationException;
import com.marcos.demoparkapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private  PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
		try{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}catch (DataIntegrityViolationException dataIntegrityViolationException){
			throw new UsernameUniqueViolationException(String.format("already registered username {%s}", user.getUsername()));

		}
	}
	
	@Transactional
	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UserIdEntityNotFoundException("User not found")
		);
	}
	
	@Transactional
	public List<User> finAllUsers() {
		return userRepository.findAll();
	}
	@Transactional
	public User passwordEdit(Long id, String password, String newPassword, String confirmPassword) {
		if(!newPassword.equals(confirmPassword)){
			throw new PasswordInvalidException("The new password is not the same as confirmation");
		}
		User user = searchById(id);
		if(!passwordEncoder.matches(password, user.getPassword())){
			throw new PasswordInvalidException("Passwords do not match");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		return user;
	}

	@Transactional
	public User deleteById(Long id) {
		User idUser = searchById(id);
		if(idUser != null) {
			userRepository.deleteById(id);
		}else {
			throw new UserIdEntityNotFoundException("User not found");
		}
        return null;
    }
	@Transactional
	public User searchByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException(String.format("User with 'username' not found", username))
		);
	}

	@Transactional
	public User.Role searchRoleByUsername(String username) {
		return userRepository.findRoleByUsername(username);
	}
	

}
