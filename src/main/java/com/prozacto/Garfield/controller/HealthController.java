package com.prozacto.Garfield.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/health")
public class HealthController {

    private Boolean healthStatus = true;

    @GetMapping
    @ResponseBody
    Boolean health() {
        return healthStatus;
    }

    @PostMapping("/toggle")
    @ResponseBody
    void toggleHealth() {
        healthStatus = !healthStatus;
    }
}
