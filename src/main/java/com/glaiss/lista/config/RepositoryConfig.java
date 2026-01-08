package com.glaiss.lista.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.glaiss.lista.domain.model")
@EnableJpaRepositories(basePackages = {"com.glaiss.lista.domain"})
public class RepositoryConfig {

}

