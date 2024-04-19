package com.marcos.demoparkapi.dto;

public class UserPasswordDto {

	private String password;
	private String newPassword;
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
