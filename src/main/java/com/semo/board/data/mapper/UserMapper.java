package com.semo.board.data.mapper;

import com.semo.board.data.request.UserRequestDTO;
import com.semo.board.data.response.UserResponseDTO;
import com.semo.board.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "userName", target = "userName")
    UserResponseDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserRequestDTO dto);
    UserRequestDTO toRequestDTO(UserEntity user);
}