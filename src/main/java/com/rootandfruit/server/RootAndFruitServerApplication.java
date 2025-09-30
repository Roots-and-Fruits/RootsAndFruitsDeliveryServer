package com.rootandfruit.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@OpenAPIDefinition(servers = {@Server(url = "https://server.yeolmae.store", description = "product yeolmae server")})
@SpringBootApplication
public class RootAndFruitServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RootAndFruitServerApplication.class, args);
    }

}
