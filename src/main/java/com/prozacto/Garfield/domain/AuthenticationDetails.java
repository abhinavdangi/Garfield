package com.prozacto.Garfield.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDetails {
    String token;
    String name;
}
