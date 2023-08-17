package com.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.TestConfiguration;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.service.ManagerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ManagerService managerService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
//                .alwaysDo(resultHandler)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("매니저 생성/회원가입(C)")
    public void success_manager_create() throws Exception {

        // given
        String url = "/manager";
        ManagerResponse res = new ManagerResponse();
        res.setName("name");
        res.setLoginId("test");

        given(managerService.save(any(ManagerRequest.class))).will(new Answer<ManagerResponse>() {

            @Override
            public ManagerResponse answer(InvocationOnMock invocation) throws Throwable {

                res.setId(1L);

                return res;
            }
        });

        // when
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                "{"
                                + " \"name\" : \"name\", "
                                + " \"loginId\" : \"test\", "
                                + " \"loginPwd\" : \"test\" "
                                + "}"
                        )
        )

        // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("name"))
                .andExpect(jsonPath("loginId").value("test"))
                .andExpect(jsonPath("id").value(1L))
                .andDo(document("manager-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("loginPwd").type(JsonFieldType.STRING).description("로그인 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("생성 unique id")
                        )
                ));
    }
}
