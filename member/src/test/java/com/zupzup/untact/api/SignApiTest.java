//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.model.request.MemberFindReq;
//import com.zupzup.untact.model.request.MemberLoginReq;
//import com.zupzup.untact.model.request.MemberPwdReq;
//import com.zupzup.untact.model.response.MemberLoginRes;
//import com.zupzup.untact.model.response.MemberRes;
//import com.zupzup.untact.service.auth.SignService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class SignApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private SignService signService;
//
//    @BeforeEach
//    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//                .apply(documentationConfiguration(restDocumentationContextProvider))
//                .addFilters(new CharacterEncodingFilter("UTF-8", true))
//                .alwaysDo(print())
//                .build();
//    }
//
//    @Test
//    @DisplayName("아이디찾기 - 성공")
//    public void success_find_loginId() throws Exception {
//
//        // given
//        // response 생성
//        MemberRes rs = new MemberRes();
//        rs.setId(1L);
//        rs.setLoginId("test");
//        rs.setCreated_at("2023-10-31 15:05");
//
//        // request 생성
//        MemberFindReq rq = new MemberFindReq();
//        rq.setName("test");
//        rq.setPhoneNum("010-1111-1111");
//        when(signService.findLoginId(any(MemberFindReq.class))).thenReturn(rs);
//
//        // rq -> JSON 형식으로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonContent = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post( "/find")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(jsonContent)
//                )
//                // then
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("loginId").value("test"))
//                .andExpect(jsonPath("created_at").isNotEmpty())
//                // documentation 처리
//                .andDo(
//                        document("find-id-success",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
//                                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("휴대폰 전화번호")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("(unique) ID"),
//                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                        fieldWithPath("created_at").description("생성 날짜")
//                                ))
//                );
//    }
//
//    @Test
//    @DisplayName("비밀번호 변경 - 성공")
//    public void success_change_pwd() throws Exception {
//
//        // given
//        MemberPwdReq rq = new MemberPwdReq();
//        rq.setLoginId("loginId");
//        rq.setPhoneNum("010-1111-1111");
//        rq.setLoginPwd1("test");
//        rq.setLoginPwd2("test");
//
//        when(signService.changePwd(any(MemberPwdReq.class))).thenReturn("Password Changed");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                RestDocumentationRequestBuilders.post("/change", 1L)
//                        .content(json)
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().string("Password Changed"))
//        // then
//                .andDo(document(
//                        "change_pwd_success",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("변경할 유저 로그인 아이디"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("변경할 유저 전화번호"),
//                                fieldWithPath("loginPwd1").type(JsonFieldType.STRING).description("수정된 비밀번호 1"),
//                                fieldWithPath("loginPwd2").type(JsonFieldType.STRING).description("수정된 비밀번호 2")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("로그인 - 성공")
//    public void success_login() throws Exception {
//
//        // given
//        // 로그인 request
//        MemberLoginReq rq = new MemberLoginReq();
//        rq.setLoginId("test");
//        rq.setLoginPwd("test");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rq);
//
//        // 로그인 response
//        MemberLoginRes rs = new MemberLoginRes();
//        rs.setLoginId("test");
//        rs.setToken("test token");
//        rs.setId(1L);
//        rs.setCnt(0);
//        when(signService.login(any(MemberLoginReq.class))).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                post("/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//        )
//                .andExpect(status().isOk())
//        // then
//                .andDo(
//                        document("login-success",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                        fieldWithPath("loginPwd").type(JsonFieldType.STRING).description("로그인 비밀번호")
//                                ),
//                                responseFields(
//                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                        fieldWithPath("token").type(JsonFieldType.STRING).description("BEARER 토큰"),
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Long 타입 유저 unique Id"),
//                                        fieldWithPath("cnt").type(JsonFieldType.NUMBER).description("int 타입 유저 가게 신청 횟수")
//                                )
//                        ));
//    }
//}
