package io.enoy.tg.example;

import io.enoy.tg.EnableTgBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableTgBot
@ComponentScan("io.enoy.tg.example.actions")
public class SpringTelegramExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTelegramExampleApplication.class, args);
    }

}
