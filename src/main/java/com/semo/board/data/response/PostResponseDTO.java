package com.semo.board.data.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String title;
    private String content;
}
