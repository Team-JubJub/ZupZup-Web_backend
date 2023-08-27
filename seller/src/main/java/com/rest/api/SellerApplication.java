package com.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@EntityScan(basePackages = {"com.zupzup", "com.rest"})
@ComponentScan({"com.zupzup.untact", "com.rest.api"})
@EnableJpaRepositories(basePackages = {"com.zupzup.untact.repository", "com.rest.api.repository"})
@EnableAsync
@EnableScheduling
public class SellerApplication {
    public static void main(String[] args) { SpringApplication.run(SellerApplication.class, args); }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

}