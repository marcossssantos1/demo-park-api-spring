package com.marcoss.demoparkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.marcoss.demoparkapi.entity.ClienteVaga;
import com.marcoss.demoparkapi.web.dto.EstacionamentoCreateDto;
import com.marcoss.demoparkapi.web.dto.EstacionamentoResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
