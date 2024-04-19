package com.marcos.demoparkapi.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.marcos.demoparkapi.dto.UserCreateDto;
import com.marcos.demoparkapi.dto.UserResponseDto;
import com.marcos.demoparkapi.entities.User;

public class UserMapper {

	public static User toUserCreate(UserCreateDto createDTO) {
		return new ModelMapper().map(createDTO, User.class);
	}

	public static UserResponseDto toUserResponse(User user) {
		String role = user.getRole().name().substring("ROLE_".length());
		PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
			@Override
			protected void configure() {
				map().setRole(role);
			}
		};
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(props);
		return mapper.map(user, UserResponseDto.class);
	}

}
