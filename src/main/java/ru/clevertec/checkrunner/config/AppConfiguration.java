package ru.clevertec.checkrunner.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan(basePackages = "ru.clevertec")
@EnableJpaRepositories("ru.clevertec.checkrunner.repository.springdata")
public class AppConfiguration {
}
