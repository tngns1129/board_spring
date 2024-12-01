package com.semo.board.data.mapper;

import com.semo.board.data.request.CommentRequestDTO;
import com.semo.board.data.response.CommentResponseDTO;
import com.semo.board.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "content", target = "content")
    CommentResponseDTO toDTO(CommentEntity entity);

    CommentEntity toEntity(CommentRequestDTO dto);

    default Page<CommentResponseDTO> toDTOPage(Page<CommentEntity> comment) {
        return comment.map(this::toDTO);
    }

}
