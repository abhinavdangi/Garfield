package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public void home(HttpServletResponse response) throws IOException {
        HttpResponseUtil
                .returnResponse(response, new HttpResponse(HttpStatus.OK, "Welcome to Garfield"));
    }
}
