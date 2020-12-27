package com.prozacto.Garfield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.prozacto.Garfield.repository")
public class GarfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarfieldApplication.class, args);
    }

}
