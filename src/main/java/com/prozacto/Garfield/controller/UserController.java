package com.prozacto.Garfield.controller;

import static com.prozacto.Garfield.utils.HttpResponseUtil.returnResponse;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserController(
            UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping(value = "/id-by-email")
    public void getIdByEmail(@RequestParam("email") String email,
                               HttpServletResponse response) throws IOException {
        returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                String.valueOf(userProfileRepository.getUserProfileByUserEmail(email).getId())));
    }

    @GetMapping(value = "/id-by-user-name")
    public void getIdByUserName(@RequestParam("user-name") String userName,
                               HttpServletResponse response) throws IOException {
        returnResponse(response, new HttpResponse(
                HttpStatus.OK,
                String.valueOf(userProfileRepository.getUserProfile(userName).getId())));
    }
}
