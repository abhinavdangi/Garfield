package com.prozacto.Garfield.domain.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserProfileDto implements Serializable {

    private static final long serialVersionUID = 2299427881293865437L;

    private long id;
    private String firstName;
    private String lastName;
    private String salt;
    private String token;
    private String userPassword;
    private String userName;
}
