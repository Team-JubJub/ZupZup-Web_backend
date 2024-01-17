//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.exception.member.MemberException;
//import com.zupzup.untact.exception.store.StoreException;
//import com.zupzup.untact.model.request.EnterReq;
//import com.zupzup.untact.model.response.EnterRes;
//import com.zupzup.untact.service.impl.EnterServiceImpl;
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
//import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
//import static com.zupzup.untact.exception.store.StoreExceptionType.CANNOT_APPLY_TWICE;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class EnterApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private EnterServiceImpl enterService;
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
//    @DisplayName("입점 신청 - 성공")
//    public void success_save() throws Exception {
//
//        // given
//        // Request 설정
//        EnterReq rq = new EnterReq();
//        rq.setId(1L);
//        rq.setName("사장님 이름");
//        rq.setPhoneNum("010-1111-1111");
//        rq.setStoreName("test storeName");
//        rq.setStoreAddress("test address");
//        rq.setLatitude(12.1111);
//        rq.setLongitude(13.1111);
//        rq.setCrNumber("test cr Num");
//
//        // Response 설정
//        EnterRes rs = new EnterRes();
//        rs.setStoreName("test storeName");
//
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        given(enterService.save(any(EnterReq.class))).will((Answer<EnterRes>) invocation -> {
//
//            // memberService 에서 save 메소드 통과시에 id 추가
//            rs.setId(1L);
//            return rs;
//        });
//
//        // when
//        mockMvc.perform(
//                        post("/enter")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(jsonRq)
//                )
//                // then
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").value(1L))
//                .andExpect(jsonPath("storeName").value("test storeName"))
//                .andDo(
//                        document(
//                                "success-save-enter",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("사장님 unique Id"),
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                        fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도 (double)"),
//                                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도 (double)"),
//                                        fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록 번호")
//                                ),
//                                responseFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("unique id"),
//                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("입점 신청 - 실패 (1번 이상 신청)")
//    public void fail_enter_twice() throws Exception {
//
//        // given
//        EnterReq rq = new EnterReq();
//        rq.setId(1L);
//        rq.setName("사장님 이름");
//        rq.setPhoneNum("010-1111-1111");
//        rq.setStoreName("test storeName");
//        rq.setStoreAddress("test address");
//        rq.setLongitude(12.345);
//        rq.setLatitude(67.890);
//        rq.setCrNumber("test 사업자번호");
//
//        String enterRq = objectMapper.writeValueAsString(rq);
//
//        when(enterService.save(any(EnterReq.class))).thenThrow(new StoreException(CANNOT_APPLY_TWICE));
//
//        // when & then
//        mockMvc.perform(
//                        post("/enter")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(enterRq)
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(
//                        document(
//                                "fail-save-enter-apply-twice",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("사장님 unique Id"),
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                        fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
//                                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
//                                        fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록 번호")
//                                )
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("입점 신청 - 실패 (사장님 id 찾지 못함)")
//    public void fail_cannot_find_member() throws Exception {
//
//        // given
//        EnterReq rq = new EnterReq();
//        rq.setId(1L);
//        rq.setName("사장님 이름");
//        rq.setPhoneNum("010-1111-1111");
//        rq.setStoreName("test storeName");
//        rq.setStoreAddress("test address");
//        rq.setLongitude(12.345);
//        rq.setLatitude(67.890);
//        rq.setCrNumber("test 사업자번호");
//
//        String enterRq = objectMapper.writeValueAsString(rq);
//
//        when(enterService.save(any(EnterReq.class))).thenThrow(new MemberException(NOT_FOUND_MEMBER));
//
//        // when & then
//        mockMvc.perform(
//                        post("/enter")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(enterRq)
//                )
//                .andExpect(status().isBadRequest())
//                .andDo(
//                        document(
//                                "fail-save-enter-cannot-find-member",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint()),
//                                requestFields(
//                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("사장님 unique Id"),
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
//                                        fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                        fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                        fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
//                                        fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
//                                        fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록 번호")
//                                )
//                        )
//                );
//    }
//
//}
