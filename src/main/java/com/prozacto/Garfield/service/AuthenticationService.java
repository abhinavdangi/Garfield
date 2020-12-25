package com.prozacto.Garfield.service;

import com.prozacto.Garfield.domain.UserRegistration;
import com.prozacto.Garfield.domain.dto.UserProfileDto;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.UserServiceException;

import java.security.spec.InvalidKeySpecException;

public interface AuthenticationService {
    UserProfileDto authenticate(String userName,
                                String userPassword)
            throws AuthenticationException, UserServiceException;

    UserProfileDto resetSecurityCredentials(String password,
                                            UserProfileDto userProfile) throws UserServiceException;

    String issueSecureToken(UserProfileDto userProfile)
            throws AuthenticationException, UserServiceException;

    Boolean checkToken(String userName, String token)
            throws InvalidKeySpecException, UserServiceException;

    void register(UserRegistration userRegistration) throws UserServiceException;
}
