package com.semo.board.data.response;

import com.semo.board.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String createDate;
    private String updateDate;
    private UserResponseDTO user;
    private Long commentCount;
}
