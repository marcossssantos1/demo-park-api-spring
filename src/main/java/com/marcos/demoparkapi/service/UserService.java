package com.marcos.demoparkapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcos.demoparkapi.entities.User;
import com.marcos.demoparkapi.exceptions.PasswordInvalidException;
import com.marcos.demoparkapi.exceptions.UserIdEntityNotFoundException;
import com.marcos.demoparkapi.exceptions.UsernameUniqueViolationException;
import com.marcos.demoparkapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User save(User user) {
		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException dataIntegrityViolationException) {
			throw new UsernameUniqueViolationException(
					String.format("already registered username {%s}", user.getUsername()));

		}
	}

	public User searchById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserIdEntityNotFoundException("Usuario não encontrado"));
	}

	@Transactional
	public User updatePassword(Long id, String password, String newPassword, String confirmPassword) {

		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordInvalidException("The new password is not the same as confirmation!");
		}
		User update = searchById(id);
		if (!update.getPassword().equals(password)) {
			throw new PasswordInvalidException("The password does not match the one created previously");
		}
		update.setPassword(password);
		return update;
	}

	@Transactional
	public List<User> finAllUsers() {
		return userRepository.findAll();
	}

}
