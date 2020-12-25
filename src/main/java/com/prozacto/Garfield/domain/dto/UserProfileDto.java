package com.prozacto.Garfield.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProfileDto {

    private String salt;
    private String token;
    private String userPassword;
    private String userName;
}
