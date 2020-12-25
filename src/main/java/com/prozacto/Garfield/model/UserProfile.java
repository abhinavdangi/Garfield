package com.prozacto.Garfield.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 7290798953394355234L;

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String salt;
    private String token;
    private String userPassword;
    private String userName;

}