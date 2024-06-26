package com.zupzup.untact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan(basePackages = {"com.zupzup"})
//@EnableJpaRepositories(basePackages = {"com.zupzup.untact.repository"})
//@EnableAsync
//@EnableScheduling
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
//
//    @Bean
//    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter();
//    }
}