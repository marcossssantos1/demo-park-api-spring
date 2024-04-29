package com.marcos.demoparkapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.marcos.demoparkapi.dto.PagebleDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {
	
	public static PagebleDto toDto(Page page) {
        return new ModelMapper().map(page, PagebleDto.class);
    }

}
