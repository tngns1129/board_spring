package com.semo.board.data.response;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String userName;
    public UserResponseDTO(String userName, String userPassword){
        this.userName = userName;
    }
    public UserResponseDTO(){}
}
