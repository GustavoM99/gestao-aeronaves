package com.sonda.gestaoaeronaves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GestaoAeronavesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoAeronavesApplication.class, args);
        System.out.println("\n=================================================");
        System.out.println("Gestao de Aeronaves API está rodando!");
        System.out.println("API: http://localhost:8080/");
        System.out.println("=================================================\n");
    }
}
