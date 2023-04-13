package com.example.alumninetwork;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Alumni Network API",
        description = "A REST API to provide access to users, posts and events, and the groups and topics those users, posts and events are associated with. Created by Anette Londal, Adrian Friduson & Marcus Vinje Johansen.",
        version = "1.0"
))
public class AlumniNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlumniNetworkApplication.class, args);
    }

}
