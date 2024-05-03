package com.marcoss.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.marcoss.demoparkapi.entity.Vaga;
import com.marcoss.demoparkapi.web.dto.VagaCreateDto;
import com.marcoss.demoparkapi.web.dto.VagaResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
