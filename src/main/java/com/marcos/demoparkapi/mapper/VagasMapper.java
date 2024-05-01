package com.marcos.demoparkapi.mapper;

import org.modelmapper.ModelMapper;

import com.marcos.demoparkapi.dto.VagaCreateDto;
import com.marcos.demoparkapi.dto.VagaResponseDto;
import com.marcos.demoparkapi.entities.Vaga;

public class VagasMapper {
	
	 public static Vaga toVacancyCreateDTO(VagaCreateDto createDTO){
	        return new ModelMapper().map(createDTO, Vaga.class);
	    }

	    public static VagaResponseDto toVacancyResponseDTO(Vaga vaga){
	        return new ModelMapper().map(vaga, VagaResponseDto.class);
	    }

}
