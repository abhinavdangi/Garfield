package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.domain.UserRegistration;
import com.prozacto.Garfield.repository.UserProfileRepository;
import com.prozacto.Garfield.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public AuthenticationServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void register(UserRegistration userRegistration) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String secureUserPassword = bCryptPasswordEncoder.encode(userRegistration.getUserPassword());
        userProfileRepository.insert(userRegistration.getFirstName(),
                                     userRegistration.getLastName(),
                                     secureUserPassword,
                                     userRegistration.getUserName(),
                                     userRegistration.getRole().name().toUpperCase(),
                                     String.valueOf(System.currentTimeMillis()),
                                     userRegistration.getUserEmail());
    }

}
