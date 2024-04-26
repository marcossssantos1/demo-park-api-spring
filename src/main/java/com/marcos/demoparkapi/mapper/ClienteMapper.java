package com.marcos.demoparkapi.mapper;

import org.modelmapper.ModelMapper;

import com.marcos.demoparkapi.dto.ClienteCreateDto;
import com.marcos.demoparkapi.dto.ClienteResponseDto;
import com.marcos.demoparkapi.entities.Cliente;

public class ClienteMapper {
	
	 public static Cliente toCustomertCreate(ClienteCreateDto customerCreateDTO){
	        return new ModelMapper().map(customerCreateDTO, Cliente.class);
	    }

	    public static ClienteResponseDto toClientResponse(Cliente customer){
	        return new ModelMapper().map(customer, ClienteResponseDto.class);
	    }

}
