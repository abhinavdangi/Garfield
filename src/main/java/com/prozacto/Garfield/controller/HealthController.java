package com.prozacto.Garfield.controller;

import com.prozacto.Garfield.domain.HttpResponse;
import com.prozacto.Garfield.utils.HttpResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/health")
public class HealthController {

    private Boolean healthStatus = true;

    @GetMapping
    @ResponseBody
    public void health(HttpServletResponse response) throws IOException {
        HttpResponseUtil
                .returnResponse(response, new HttpResponse(HttpStatus.OK, healthStatus.toString()));

    }

    @PostMapping("/toggle")
    @ResponseBody
    public void toggleHealth(HttpServletResponse response) throws IOException {
        healthStatus = !healthStatus;
        HttpResponseUtil.returnResponse(response, new HttpResponse(HttpStatus.OK, "Successful"));
    }
}
