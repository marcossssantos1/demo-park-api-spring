package com.marcos.demoparkapi.dto;

import org.modelmapper.ModelMapper;

import com.marcos.demoparkapi.entities.Cliente;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteResponseDto {
	
	public static Cliente toClienteCreate(ClienteCreateDto customerCreateDTO){
        return new ModelMapper().map(customerCreateDTO, Cliente.class);
    }

    public static ClienteResponseDto toClientResponse(Cliente customer){
        return new ModelMapper().map(customer, ClienteResponseDto.class);
    }

}
