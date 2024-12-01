package com.semo.board.data.response;

import com.semo.board.entity.UserEntity;
import lombok.Data;

@Data
public class CommentResponseDTO {
    private Long id;
    private String content;
    private UserEntity user;
}
