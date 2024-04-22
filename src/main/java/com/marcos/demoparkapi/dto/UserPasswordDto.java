package com.marcos.demoparkapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserPasswordDto {

	@NotBlank
	@Size(min = 6, max = 6)
	private String password;
	@NotBlank
	@Size(min = 6, max = 6)
	private String newPassword;
	@NotBlank
	@Size(min = 6, max = 6)
	private String confirmPassword;

	public UserPasswordDto() {
		// TODO Auto-generated constructor stub
	}

	public UserPasswordDto(String password, String newPassword, String confirmPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UserPasswordDto [password=" + password + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + "]";
	}

}
