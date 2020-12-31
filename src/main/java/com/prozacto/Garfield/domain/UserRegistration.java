package com.prozacto.Garfield.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRegistration {
    private String firstName;
    private String lastName;
    private String userPassword;
    private String userName;
    private Role role;
    private String userEmail;
}
