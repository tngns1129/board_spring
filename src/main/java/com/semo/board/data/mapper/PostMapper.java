package com.semo.board.data.mapper;

import com.semo.board.data.request.PostRequestDTO;
import com.semo.board.data.response.PostResponseDTO;
import com.semo.board.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    PostResponseDTO toDTO(PostEntity entity);

    List<PostResponseDTO> toDTOList(List<PostEntity> entities);

    default Page<PostResponseDTO> toDTOPage(Page<PostEntity> page) {
        return page.map(this::toDTO);
    }

    PostEntity toEntity(PostRequestDTO dto);

    @Mapping(target = "id", ignore = true)  // id는 업데이트 시 변경하지 않도록 무시
    void updateEntityFromDto(PostRequestDTO dto, @MappingTarget PostEntity entity);

}
