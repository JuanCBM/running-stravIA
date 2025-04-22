package com.wikiblaz.straviarunning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.wikiblaz.straviarunning.domain",
        "com.wikiblaz.straviarunning.application",
        "com.wikiblaz.straviarunning.persistence",
        "com.wikiblaz.straviarunning.rest"
})
@EntityScan("com.wikiblaz.straviarunning.persistence.entity")
@EnableJpaRepositories("com.wikiblaz.straviarunning.persistence.repository")
public class StraviaRunningApplication {

    public static void main(String[] args) {
        SpringApplication.run(StraviaRunningApplication.class, args);
    }
}
