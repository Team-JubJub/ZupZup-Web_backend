package com.rest.api;

import com.zupzup.untact.ManagerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = ManagerApplication.class)
public class TestConfiguration {

    @Test
    void contextLoads() {}
}
