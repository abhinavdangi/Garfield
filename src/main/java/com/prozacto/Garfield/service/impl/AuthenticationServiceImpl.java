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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value(value = "${token.alive.time.min}")
    private Long TOKEN_ALIVE_TIME_IN_MIN;

    private static final int SALT_LENGTH = 30;

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

        if (userProfile == null || userProfile.getUserName() == null) {
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
        String salt = authenticationUtil.generateSalt(SALT_LENGTH);
        String secureUserPassword = authenticationUtil.generateSecurePassword(password, salt);
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
        String newSaltAsPostfix = userProfileDto.getSalt();
        String accessTokenMaterial = userProfileDto.getUserName() + newSaltAsPostfix;
        byte[] encryptedAccessToken;
        encryptedAccessToken =
                authenticationUtil.encrypt(userProfileDto.getUserPassword(), accessTokenMaterial);
        String encryptedAccessTokenBase64Encoded =
                Base64.getEncoder().encodeToString(encryptedAccessToken);
        int tokenLength = encryptedAccessTokenBase64Encoded.length();
        String tokenToSaveToDatabase =
                encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnToken = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
        userProfileRepository.setToken(tokenToSaveToDatabase, userProfileDto.getUserName(),
                                       String.valueOf(System.currentTimeMillis()));
        return returnToken;
    }

    @Override
    public Boolean checkToken(String userName, String token)
            throws UserServiceException, AuthenticationException {
        UserProfile userProfile = userProfileRepository.getUserProfile(userName);
        if (userProfile == null || userProfile.getUserName() == null) {
            throw new AuthenticationException(userName + " has not registered. Please register!");
        }
        Long timeDifference = System.currentTimeMillis() - Long.valueOf(userProfile.getCreatedOn());
        LOG.info(timeDifference.toString());
        if (timeDifference > TOKEN_ALIVE_TIME_IN_MIN * 1000 * 60) {
            throw new AuthenticationException(userName + "has been idle for more than "
                                              + TOKEN_ALIVE_TIME_IN_MIN + " minutes. "
                                              + "Please login and recreate the token.");
        }
        String accessTokenMaterial = userName + userProfile.getSalt();
        byte[] encryptedAccessToken =
                authenticationUtil.encrypt(userProfile.getUserPassword(), accessTokenMaterial);
        String encryptedAccessTokenBase64Encoded =
                Base64.getEncoder().encodeToString(encryptedAccessToken);

        if (encryptedAccessTokenBase64Encoded.equals(userProfile.getToken() + token)) {
            return true;
        } else {
            throw new AuthenticationException("Not authorized");
        }
    }

    @Override
    public void register(UserRegistration userRegistration) throws UserServiceException {
        String salt = authenticationUtil.generateSalt(SALT_LENGTH);
        String secureUserPassword;
        secureUserPassword =
                authenticationUtil.generateSecurePassword(userRegistration.getUserPassword(), salt);
        userProfileRepository.insert(userRegistration.getFirstName(),
                                     userRegistration.getLastName(),
                                     salt,
                                     secureUserPassword,
                                     userRegistration.getUserName(),
                                     userRegistration.getRole().name().toUpperCase(),
                                     String.valueOf(System.currentTimeMillis()));
    }

}
