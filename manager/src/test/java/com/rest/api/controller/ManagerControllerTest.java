package com.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.api.TestConfiguration;
import com.rest.api.documents.utils.RestDocsConfig;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.service.ManagerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Import(RestDocsConfig.class)
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = {TestConfiguration.class})
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ManagerService managerService;

    private final String url = "/manager";

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
    @DisplayName("매니저 생성/회원가입(C) - 성공")
    public void success_create_manager() throws Exception {

        // given
        // manager response 생성
        ManagerResponse res = new ManagerResponse();
        res.setName("name");
        res.setLoginId("test");

        given(managerService.save(any(ManagerRequest.class))).will((Answer<ManagerResponse>) invocation -> {

            // managerService 에서 save 메소드 통과시에 id 추가
            res.setId(1L);
            return res;
        });

        // when
        mockMvc.perform(
                // POST 로 url 에 요청 보내기
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        // request 예시
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
                // 문서화
                .andDo(document("manager-create",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("loginPwd").type(JsonFieldType.STRING).description("로그인 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("(저장된) 매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("(저장된) 로그인 아이디"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("(생성된) unique id")
                        )
                ));
    }

    @Test
    @DisplayName("매니저 조회 - 성공")
    public void success_read_manager() throws Exception {

        // given
        // 더미 데이터 생성
        List<ManagerResponse> resList = new ArrayList<>();
        ManagerResponse res1 = new ManagerResponse();
        res1.setId(1L);
        res1.setName("name1");
        res1.setLoginId("login1");
        resList.add(res1);

        ManagerResponse res2 = new ManagerResponse();
        res2.setId(2L);
        res2.setName("name2");
        res2.setLoginId("login2");
        resList.add(res2);

        ManagerResponse res3 = new ManagerResponse();
        res3.setId(3L);
        res3.setName("name3");
        res3.setLoginId("login3");
        resList.add(res3);
        given(managerService.findAll()).willReturn(resList);

        // when
        mockMvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        )

        // then
                .andExpect(status().isOk())
                .andDo(
                        // 문서 작성
                        document("manager-get",
                                responseFields(
                                        fieldWithPath("[].name").type(JsonFieldType.STRING).description("매니저 이름"),
                                        fieldWithPath("[].loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("매니저 unique id")
                                )
                        )
                );
    }

    @Test
    @DisplayName("매니저 업데이트 - 성공")
    public void success_update_manager() throws Exception {

        // given
        ManagerResponse res = new ManagerResponse();
        res.setLoginId("test22");
        res.setId(1L);
        res.setName("test");
        given(managerService.update(any(ManagerRequest.class))).willReturn(res);

        // when
        mockMvc.perform(
                patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        // request 예시
                        .content(
                                "{"
                                        + " \"name\" : \"name\", "
                                        + " \"loginId\" : \"test\", "
                                        + " \"loginPwd\" : \"test\", "
                                        + " \"id\" : 1 "
                                        + "}"
                        )
        )

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("test"))
                .andExpect(jsonPath("loginId").value("test22"))
                .andExpect(jsonPath("id").value(1L))
                // 문서화
                .andDo(document("manager-update",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("(변경할) 매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("(변경할) 로그인 아이디"),
                                fieldWithPath("loginPwd").type(JsonFieldType.STRING).description("(변경할) 로그인 비밀번호"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("매니저 unique id")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("(변경된) 매니저 이름"),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("(변경된) 로그인 아이디"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("unique id")
                        )
                ));
    }

//    @Test
//    @DisplayName("매니저 업데이트: null 값 들어올 경우 - 실패")
//    public void failure_update_manager_nullException() throws Exception {
//
//        // given
//
//        // when
//
//        // then
//    }

    @Test
    @DisplayName("매니저 삭제 - 성공")
    public void success_delete_manager() throws Exception {
        // given
        Long res = 1L;
        given(managerService.delete(1L)).willReturn(res);

        // when
        mockMvc.perform(
                        patch(url+"/{managerId}", 1L)
                )

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(equalTo(res)))
                .andDo(
                        document("manager-delete",
                                responseFields(
                                        fieldWithPath("$").type(JsonFieldType.NUMBER).description("(삭제된) id")
                                )
                        )
                );
    }

}
