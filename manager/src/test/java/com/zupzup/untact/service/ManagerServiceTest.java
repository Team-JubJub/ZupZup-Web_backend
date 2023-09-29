package com.zupzup.untact.service;

import com.zupzup.untact.TestApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Transactional
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = TestApplication.class)
@AutoConfigureMockMvc
public class ManagerServiceTest {
}
