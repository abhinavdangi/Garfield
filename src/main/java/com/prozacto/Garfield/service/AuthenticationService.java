package com.prozacto.Garfield.service;

import com.prozacto.Garfield.domain.dto.UserProfileDto;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.UserServiceException;

public interface AuthenticationService {
    UserProfileDto authenticate(String userName,
                                String userPassword) throws AuthenticationException;

    void resetSecurityCredentials(String password,
                                            UserProfileDto userProfile) throws UserServiceException;

    String issueSecureToken(UserProfileDto userProfile) throws AuthenticationException;
}
