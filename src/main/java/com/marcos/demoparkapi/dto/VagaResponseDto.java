package com.marcos.demoparkapi.dto;

public class VagaResponseDto {

	private String id;
	private String code;
	private String status;

	public VagaResponseDto() {
		// TODO Auto-generated constructor stub
	}

	public VagaResponseDto(String id, String code, String status) {
		super();
		this.id = id;
		this.code = code;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
