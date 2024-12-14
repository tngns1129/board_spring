package com.semo.board.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDTO {
    private String title;
    private String content;

    @Builder
    public PostRequestDTO(String title, String content){
        this.title = title;
        this.content = content;
    }
}
