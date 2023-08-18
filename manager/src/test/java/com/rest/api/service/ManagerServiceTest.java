package com.rest.api.service;

import com.rest.api.TestConfiguration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Transactional
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class ManagerServiceTest {
}
