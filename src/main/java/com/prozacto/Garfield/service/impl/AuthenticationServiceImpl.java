package com.prozacto.Garfield.service.impl;

import com.prozacto.Garfield.domain.UserRegistration;
import com.prozacto.Garfield.domain.dto.UserProfileDto;
import com.prozacto.Garfield.exception.AuthenticationException;
import com.prozacto.Garfield.exception.UserServiceException;
import com.prozacto.Garfield.model.UserProfile;
import com.prozacto.Garfield.repository.UserProfileRepository;
import com.prozacto.Garfield.service.AuthenticationService;
import com.prozacto.Garfield.utils.AuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private UserProfileRepository userProfileRepository;
    private AuthenticationUtil authenticationUtil;

    @Autowired
    public AuthenticationServiceImpl(UserProfileRepository userProfileRepository,
                                     AuthenticationUtil authenticationUtil) {
        this.userProfileRepository = userProfileRepository;
        this.authenticationUtil = authenticationUtil;
    }

    @Override
    public UserProfileDto authenticate(String userName, String userPassword)
            throws AuthenticationException, UserServiceException {
        UserProfileDto userProfileDto = new UserProfileDto();
        UserProfile userProfile = userProfileRepository.getUserProfile(userName);

        if(userProfile == null || userProfile.getUserName() == null){
            throw new AuthenticationException(userName + " has not registered. Please register!");
        }
        String secureUserPassword;
        secureUserPassword = authenticationUtil.
                generateSecurePassword(userPassword, userProfile.getSalt());

        if (secureUserPassword != null &&
            secureUserPassword.equalsIgnoreCase(userProfile.getUserPassword()) &&
            userName != null &&
            userName.equalsIgnoreCase(userProfile.getUserName())) {
            BeanUtils.copyProperties(userProfile, userProfileDto);
            return userProfileDto;
        } else {
            throw new AuthenticationException("Authentication failed");
        }
    }

    @Override
    public UserProfileDto resetSecurityCredentials(String password, UserProfileDto userProfileDto)
            throws UserServiceException {
        // Generate salt
        String salt = authenticationUtil.generateSalt(30);
        // Generate secure user password
        String secureUserPassword = authenticationUtil.generateSecurePassword(password, salt);
        //update salt and user password in database
        userProfileRepository.setSalt(salt, userProfileDto.getUserName());
        userProfileRepository.setUserPassword(secureUserPassword, userProfileDto.getUserName());
        userProfileDto.setSalt(salt);
        userProfileDto.setUserPassword(secureUserPassword);
        return userProfileDto;
    }

    @Override
    public String issueSecureToken(UserProfileDto userProfileDto)
            throws UserServiceException {
        String returnToken;

        // Get salt but only part of it
        String newSaltAsPostfix = userProfileDto.getSalt();
        String accessTokenMaterial = userProfileDto.getUserName() + newSaltAsPostfix;

        byte[] encryptedAccessToken;

        encryptedAccessToken = authenticationUtil.encrypt(userProfileDto.getUserPassword(), accessTokenMaterial);

        String encryptedAccessTokenBase64Encoded =
                Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();
        String tokenToSaveToDatabase =
                encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnToken = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfileRepository.setToken(tokenToSaveToDatabase, userProfileDto.getUserName());
        return returnToken;
    }

    @Override
    public Boolean checkToken(String userName, String token) throws UserServiceException {
        UserProfile userProfile = userProfileRepository.getUserProfile(userName);
        String accessTokenMaterial = userName + userProfile.getSalt();
        byte[] encryptedAccessToken =
                authenticationUtil.encrypt(userProfile.getUserPassword(), accessTokenMaterial);
        String encryptedAccessTokenBase64Encoded =
                Base64.getEncoder().encodeToString(encryptedAccessToken);

        return encryptedAccessTokenBase64Encoded.equals(userProfile.getToken() + token);
    }

    @Override
    public void register(UserRegistration userRegistration) throws UserServiceException {
        String salt = authenticationUtil.generateSalt(30);
        String secureUserPassword;
        secureUserPassword = authenticationUtil.generateSecurePassword(userRegistration.getUserPassword(), salt);
        //update salt and user password in database
        userProfileRepository.insert(userRegistration.getFirstName(),
                                     userRegistration.getLastName(),
                                     salt,
                                     secureUserPassword,
                                     userRegistration.getUserName(),
                                     userRegistration.getRole().name().toUpperCase(),
                                     String.valueOf(System.currentTimeMillis()));
    }

}
