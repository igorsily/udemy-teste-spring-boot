package com.br.igorsily.udemytestespringboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class UdemyTesteSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemyTesteSpringBootApplication.class, args);

        log.info("Teste de log 2");
        log.info("Teste de log 3");
        log.info("Teste de log 4");
    }

}
