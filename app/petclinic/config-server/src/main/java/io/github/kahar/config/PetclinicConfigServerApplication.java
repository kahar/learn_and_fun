package io.github.kahar.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class PetclinicConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetclinicConfigServerApplication.class, args);
    }
}