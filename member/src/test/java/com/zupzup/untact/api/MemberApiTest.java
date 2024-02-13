//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.exception.member.MemberException;
//import com.zupzup.untact.model.request.MemberCheckDuplicateLoginIdReq;
//import com.zupzup.untact.model.request.MemberReq;
//import com.zupzup.untact.model.response.MemberRes;
//import com.zupzup.untact.service.impl.MemberServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import static com.zupzup.untact.exception.member.MemberExceptionType.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class MemberApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MemberServiceImpl memberService;
//
//    private final String url = "/member";
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
//    @DisplayName("사장님 생성/회원가입(C) - 성공")
//    public void success_save() throws Exception {
//
//        // given
//        // response 생성
//        MemberRes rs = new MemberRes();
//        rs.setId(1L);
//        rs.setLoginId("test");
//        rs.setCreated_at("2023-10-03 15:05");
//
//        given(memberService.save(any(MemberReq.class))).will((Answer<MemberRes>) invocation -> {
//
//            // memberService 에서 save 메소드 통과시에 id 추가
//            rs.setId(1L);
//            return rs;
//        });
//
//        // when
//        mockMvc.perform(
//                // POST 요청
//                post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(
//                                "{"
//                                + " \"name\": \"test\", "
//                                + " \"phoneNum\": \"010-1111-1111\", "
//                                + " \"ad\": false, "
//                                + " \"loginId\": \"test\", "
//                                + " \"loginPwd1\": \"pwd1\", "
//                                + " \"loginPwd2\": \"pwd1\", "
//                                + " \"email\": \"email\" "
//                                + "}"
//                        )
//        )
//        // then
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").value(1L))
//                .andExpect(jsonPath("loginId").value("test"))
//                .andExpect(jsonPath("created_at").isNotEmpty())
//                .andDo(document(
//                        "create-seller",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                fieldWithPath("ad").type(JsonFieldType.BOOLEAN).description("광고성 정보 수신 동의 여부"),
//                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                fieldWithPath("loginPwd1").type(JsonFieldType.STRING).description("로그인 비밀번호"),
//                                fieldWithPath("loginPwd2").type(JsonFieldType.STRING).description("로그인 비밀번호(같은지 서버에서 한 번 더 확인)"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("unique id"),
//                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                fieldWithPath("created_at").description("생성된 시간(리스트 형식으로 전달됨)")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("사장님 생성/회원가입 - 실패 - 이미 존재하는 회원")
//    public void fail_save_already_exists() throws Exception {
//
//        // given
//        when(memberService.save(any(MemberReq.class))).thenThrow(new MemberException(ALREADY_EXIST_MEMBER));
//
//        // when
//        mockMvc.perform(
//                        // POST 요청
//                        post(url)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content(
//                                        "{"
//                                                + " \"name\": \"test\", "
//                                                + " \"phoneNum\": \"010-1111-1111\", "
//                                                + " \"ad\": false, "
//                                                + " \"loginId\": \"test\", "
//                                                + " \"loginPwd1\": \"pwd1\", "
//                                                + " \"loginPwd2\": \"pwd1\", "
//                                                + " \"email\": \"email\" "
//                                                + "}"
//                                )
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-create-seller-already-exists",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                fieldWithPath("ad").type(JsonFieldType.BOOLEAN).description("광고성 정보 수신 동의 여부"),
//                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                fieldWithPath("loginPwd1").type(JsonFieldType.STRING).description("로그인 비밀번호"),
//                                fieldWithPath("loginPwd2").type(JsonFieldType.STRING).description("로그인 비밀번호(같은지 서버에서 한 번 더 확인)"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("사장님 생성/회원가입 - 실패 - 비밀번호 동일 여부 확인")
//    public void fail_save_not_same_password() throws Exception {
//
//        // given
//        when(memberService.save(any(MemberReq.class))).thenThrow(new MemberException(NOT_SAME_PASSWORD));
//
//        // when
//        mockMvc.perform(
//                        // POST 요청
//                        post(url)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .content(
//                                        "{"
//                                                + " \"name\": \"test\", "
//                                                + " \"phoneNum\": \"010-1111-1111\", "
//                                                + " \"ad\": false, "
//                                                + " \"loginId\": \"test\", "
//                                                + " \"loginPwd1\": \"pwd1\", "
//                                                + " \"loginPwd2\": \"pwd12\", "
//                                                + " \"email\": \"email\" "
//                                                + "}"
//                                )
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-create-seller-not-same-password",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                fieldWithPath("ad").type(JsonFieldType.BOOLEAN).description("광고성 정보 수신 동의 여부"),
//                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
//                                fieldWithPath("loginPwd1").type(JsonFieldType.STRING).description("로그인 비밀번호"),
//                                fieldWithPath("loginPwd2").type(JsonFieldType.STRING).description("로그인 비밀번호(같은지 서버에서 한 번 더 확인)"),
//                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("중복 아이디 확인 - 아이디 존재")
//    public void success_find_loginId() throws Exception {
//
//        // given
//        MemberCheckDuplicateLoginIdReq rq = new MemberCheckDuplicateLoginIdReq();
//        rq.setLoginId("test");
//
//        // 값 json 으로 매핑
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(rq);
//
//        when(memberService.checkLoginId(any(String.class))).thenReturn("Username is Available");
//
//        // when
//        mockMvc.perform(
//                post(url + "/check")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//        )
//        // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("Username is Available"))
//                .andDo(
//                        document(
//                                "success-check-loginId",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("loginId").description("중복 확인을 위한 로그인 아이디(String)")
//                                )
//                        )
//
//                );
//    }
//
//    @Test
//    @DisplayName("중복 아이디 확인 - 아이디 이미 존재")
//    public void fail_find_loginId() throws Exception {
//
//        // given
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        MemberCheckDuplicateLoginIdReq loginIdReq = new MemberCheckDuplicateLoginIdReq();
//        loginIdReq.setLoginId("Test Login ID");
//
//        when(memberService.checkLoginId(any(String.class))).thenThrow(new MemberException(ALREADY_EXIST_USERNAME));
//
//        String loginId = objectMapper.writeValueAsString(loginIdReq);
//
//        // when
//        mockMvc.perform(
//                post(url + "/check")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(loginId)
//        )
//        // then
//                .andExpect(status().isBadRequest())
//                .andDo(
//                        document(
//                                "error-find-loginId",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("loginId").type(JsonFieldType.STRING).description("중복 확인을 위한 로그인 아이디(String)")
//                                )
//                        )
//                );
//    }
//}
