package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.AuthenticationDetails;
import com.prozacto.Garfield.domain.LoginCredentials;
import com.prozacto.Garfield.domain.UserRegistration;
import com.prozacto.Garfield.domain.dto.UserProfileDto;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.UserServiceException;
import com.prozacto.Garfield.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    final private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(
            @Qualifier("authenticationService") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDetails> userLogin(
            @RequestBody final LoginCredentials loginCredentials)
            throws AuthenticationException, UserServiceException {
        UserProfileDto userProfile;
        userProfile = authenticationService.authenticate(loginCredentials.getUserName(),
                                                         loginCredentials.getUserPassword());
        userProfile = authenticationService.resetSecurityCredentials(
                loginCredentials.getUserPassword(), userProfile);
        String secureUserToken = authenticationService.issueSecureToken(userProfile);
        return new ResponseEntity<>(
                new AuthenticationDetails(secureUserToken, userProfile.getUserName()),
                HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody final UserRegistration userRegistration)
            throws UserServiceException {
        authenticationService.register(userRegistration);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkAuth(@RequestParam(value = "userName") String userName,
                                            @RequestParam(value = "token") String token)
            throws UserServiceException, AuthenticationException {
        authenticationService.checkToken(userName, token);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
}
