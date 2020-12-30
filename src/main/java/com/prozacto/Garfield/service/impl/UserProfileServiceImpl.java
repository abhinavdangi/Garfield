package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.model.UserProfileDetails;
import com.prozacto.Garfield.model.UserProfile;
import com.prozacto.Garfield.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceImpl implements UserDetailsService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.getUserProfile(userName);
        if (userName == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserProfileDetails(userProfile);
    }
}
