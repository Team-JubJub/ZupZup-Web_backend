//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.exception.member.MemberException;
//import com.zupzup.untact.exception.store.StoreException;
//import com.zupzup.untact.service.impl.CancelServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
//import static com.zupzup.untact.exception.store.StoreExceptionType.NO_MATCH_STORE;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class CancelApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private CancelServiceImpl cancelService;
//
//    String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZSI6W3sibmFtZSI6IlJPTEVfU0VMTEVSIn1dLCJpYXQiOjE2OTQzNDQ3MDAsImV4cCI6MTY5NDM0ODMwMH0.sLm_sw3uhWH1CbuC8MHyY1f1fAWKw6u22nynkOTzNTk";
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
//    @DisplayName("회원탈퇴 요청 - 성공")
//    public void success_wantDelete() throws Exception {
//
//        // given
//        String res = "ID: " + 1L + " is deleted";
//
//        // Response 설정
//        when(cancelService.wantDelete(1L)).thenReturn(res);
//
//        // when
//        mockMvc.perform(
//                        patch("/cancel/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(
//                        document(
//                                "success-wantDelete",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                pathParameters(
//                                    parameterWithName("id").description("사장님 unique id")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("회원탈퇴 요청 - 회원 찾기 실패")
//    public void fail_wantDelete() throws Exception {
//
//        when(cancelService.wantDelete(1L)).thenThrow(new MemberException(NOT_FOUND_MEMBER));
//
//        // when
//        mockMvc.perform(
//                        patch("/cancel/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document("fail-wantDelete-seller-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("seller unique id")
//                        )));
//    }
//
//    @Test
//    @DisplayName("회원탈퇴 요청 - 가게 찾기 실패")
//    public void fail_wantDelete_cannotFindStore() throws Exception {
//
//        when(cancelService.wantDelete(1L)).thenThrow(new StoreException(NO_MATCH_STORE));
//
//        // when
//        mockMvc.perform(
//                        patch("/cancel/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document("fail-wantDelete-store-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("seller unique id")
//                        )));
//    }
//}
