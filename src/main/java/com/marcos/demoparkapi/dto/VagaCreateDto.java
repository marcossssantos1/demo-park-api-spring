package com.marcos.demoparkapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VagaCreateDto {
	
	@NotBlank
    @Size(min = 4, max = 4)
    private String code;

    @NotBlank
    @Pattern( regexp = "FREE|BUSY")
    private String status;
    
    public VagaCreateDto() {
		// TODO Auto-generated constructor stub
	}

	public VagaCreateDto(@NotBlank @Size(min = 4, max = 4) String code,
			@NotBlank @Pattern(regexp = "FREE|BUSY") String status) {
		super();
		this.code = code;
		this.status = status;
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
