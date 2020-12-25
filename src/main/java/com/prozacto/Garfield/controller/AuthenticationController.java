package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.AuthenticationDetails;
import com.prozacto.Garfield.domain.LoginCredentials;
import com.prozacto.Garfield.domain.dto.UserProfileDto;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.UserServiceException;
import com.prozacto.Garfield.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    final private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(
            @Qualifier("authenticationService") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    @ResponseBody
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials)
            throws AuthenticationException, UserServiceException {
        UserProfileDto userProfile;
        try {
            userProfile = authenticationService.authenticate(
                    loginCredentials.getUserName(),
                    loginCredentials.getUserPassword());
        } catch (AuthenticationException ex) {
            LOG.error(ex.getMessage());
            return new AuthenticationDetails();
        }
        authenticationService.resetSecurityCredentials(loginCredentials.getUserPassword(),
                                                       userProfile);
        String secureUserToken = authenticationService.issueSecureToken(userProfile);
        return new AuthenticationDetails(secureUserToken, userProfile.getUserName());
    }
}
