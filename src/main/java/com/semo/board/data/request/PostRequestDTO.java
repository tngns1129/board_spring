package com.semo.board.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDTO {
    private String title;
    private String content;

    @Builder
    public PostRequestDTO(String title, String content){
        this.title = title;
        this.content = content;
    }
}
