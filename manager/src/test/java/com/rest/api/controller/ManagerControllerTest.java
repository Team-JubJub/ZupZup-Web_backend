package com.rest.api.controller;

import com.rest.api.TestConfiguration;
import com.rest.api.documents.utils.RestDocsConfig;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.service.ManagerService;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfig.class)
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = {TestConfiguration.class})
@AutoConfigureMockMvc
public class ManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    ObjectMapper objectMapper;

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
        ManagerRes res = new ManagerRes();
        res.setName("name");
        res.setLoginId("test");

        given(managerService.save(any(ManagerReq.class))).will((Answer<ManagerRes>) invocation -> {

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
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
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
        List<ManagerRes> resList = new ArrayList<>();
        ManagerRes res1 = new ManagerRes();
        res1.setId(1L);
        res1.setName("name1");
        res1.setLoginId("login1");
        resList.add(res1);

        ManagerRes res2 = new ManagerRes();
        res2.setId(2L);
        res2.setName("name2");
        res2.setLoginId("login2");
        resList.add(res2);

        ManagerRes res3 = new ManagerRes();
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
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
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

        Long id = 1L;
        // given
        ManagerRes res = new ManagerRes();
        res.setLoginId("test22");
        res.setId(1L);
        res.setName("test");
        given(managerService.update(eq(id), any(ManagerReq.class))).willReturn(res);

        // when
        mockMvc.perform(
                        RestDocumentationRequestBuilders.patch(url + "/{managerId}", 1L)
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
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
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
                        RestDocumentationRequestBuilders.patch(url+"/delete/{managerId}", 1L)
                )

                // then
                .andExpect(status().isOk())
                .andDo(
                        document("manager-delete",
                                pathParameters(
                                        parameterWithName("managerId").description("매니저 unique Id")
                                )
                        )
                );
    }

}
