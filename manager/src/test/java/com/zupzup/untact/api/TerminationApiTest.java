//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.exception.member.MemberException;
//import com.zupzup.untact.exception.store.StoreException;
//import com.zupzup.untact.model.dto.response.DeleteStoreListRes;
//import com.zupzup.untact.model.dto.response.StoreRes;
//import com.zupzup.untact.service.impl.TerminationServiceImpl;
//import jakarta.persistence.JoinColumn;
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
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
//import static com.zupzup.untact.exception.store.StoreExceptionType.NO_MATCH_STORE;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class TerminationApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    private TerminationServiceImpl terminationService;
//
//    private final String url = "/delete";
//    private final String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MjIiLCJyb2xlIjpbeyJuYW1lIjoiUk9MRV9NQU5BR0VSIn1dLCJpYXQiOjE2OTYyMzM1MzAsImV4cCI6MTY5NjIzNzEzMH0.Hly-SbA4XPM9Z_wO30e9rThICqwdtZb-8x0pLh1AoyA";
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
//    @DisplayName("회원탈퇴 요청 리스트 - 리스트 사이즈:0")
//    public void list_size_zero_gatherDeletionRequests() throws Exception {
//
//        // given
//        given(terminationService.gatherDeletionRequests()).willReturn(new ArrayList<>());
//
//        // when
//        mockMvc.perform(
//                get(url))
//                .andExpect(status().isOk())
//                .andExpect(content().string("[]"))
//                // then
//                .andDo(document("zero_list_gatherDeletionRequest",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[]").description("빈 리스트"))
//                ));
//    }
//
//    @Test
//    @DisplayName("회원탈퇴 요청 리스트 - 값 있음")
//    public void success_gatherDeletionRequests() throws Exception {
//
//        // given
//        DeleteStoreListRes res1 = new DeleteStoreListRes();
//        res1.setStoreId(1L);
//        res1.setSellerName("사장님 1");
//        res1.setStoreName("가게 1");
//        res1.setDeleteStatusTimestamp(timeSetter());
//
//        DeleteStoreListRes res2 = new DeleteStoreListRes();
//        res2.setStoreId(1L);
//        res2.setSellerName("사장님 1");
//        res2.setStoreName("가게 1");
//        res2.setDeleteStatusTimestamp(timeSetter());
//
//        DeleteStoreListRes res3 = new DeleteStoreListRes();
//        res3.setStoreId(1L);
//        res3.setSellerName("사장님 1");
//        res3.setStoreName("가게 1");
//        res3.setDeleteStatusTimestamp(timeSetter());
//
//        // 임의의 리스트 생성
//        List<DeleteStoreListRes> resList = new ArrayList<>();
//        resList.add(res1);
//        resList.add(res2);
//        resList.add(res3);
//
//        when(terminationService.gatherDeletionRequests()).thenReturn(resList);
//
//        // when
//        mockMvc.perform(get(url))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].storeId").value(1L))
//                .andExpect(jsonPath("$[0].sellerName").value("사장님 1"))
//                // then
//                .andDo(document("list_gatherDeletionRequests",
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[].storeId").type(JsonFieldType.NUMBER).description("가게 id Long 타입"),
//                                fieldWithPath("[].sellerName").type(JsonFieldType.STRING).description("사장님 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].deleteStatusTimestamp").type(JsonFieldType.STRING).description("탈퇴 신청 시간")
//                        )));
//
//    }
//
//    @Test
//    @DisplayName("탈퇴 요청 상세 - 성공")
//    public void success_deletionDetail() throws Exception {
//
//        // given
//        StoreRes res = new StoreRes();
//        res.setStoreId(1L);
//        res.setSellerName("사장님 1");
//        res.setSellerLoginId("aaa111");
//        res.setSellerContact("010-0000-0000");
//        res.setStoreContact("051-000-0000");
//        res.setCrNumber("123-45-67890");
//        res.setStoreName("가게 1");
//        res.setStoreAddress("주소 1");
//        res.setStoreImageUrl("--");
//        res.setCategory("CAFE");
//
//        when(terminationService.deletionDetail(1L)).thenReturn(res);
//
//        // when
//        mockMvc.perform(
//                get("/delete/{id}", 1L)
//                        .header("Authorization", "Bearer " + bearerToken))
//        // then
//                .andExpect(status().isOk())
//                .andDo(document("success-deletionDetail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                    parameterWithName("id").description("가게 unique id")
//                        ),
//                        responseFields(
//                                    fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("가게 id Long 타입"),
//                                    fieldWithPath("sellerName").type(JsonFieldType.STRING).description("사장님 이름"),
//                                    fieldWithPath("sellerLoginId").type(JsonFieldType.STRING).description("사장님 로그인 아이디"),
//                                    fieldWithPath("sellerContact").type(JsonFieldType.STRING).description("사장님 전화번호"),
//                                    fieldWithPath("storeContact").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                    fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 번호"),
//                                    fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                    fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                    fieldWithPath("storeImageUrl").type(JsonFieldType.STRING).description("이미지 url"),
//                                    fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리")
//                                ))
//                        );
//    }
//
//    @Test
//    @DisplayName("탈퇴 요청 상세 - 가게 찾지 못하는 에러")
//    public void deletionDetail_cannot_find_store() throws Exception {
//
//        when(terminationService.deletionDetail(1L)).thenThrow(new StoreException(NO_MATCH_STORE));
//
//        // when
//        mockMvc.perform(
//                        get("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("fail-deletionDetail-store-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        ),
//                        responseFields(
//                                fieldWithPath("errCode").description("The error code"),
//                                fieldWithPath("errMsg").description("The error message")
//                        )));
//    }
//
//    @Test
//    @DisplayName("탈퇴 요청 상세 - 사용자 찾지 못하는 에러")
//    public void deletionDetail_cannot_find_member() throws Exception {
//
//        when(terminationService.deletionDetail(1L)).thenThrow(new MemberException(NOT_FOUND_MEMBER));
//
//        // when
//        mockMvc.perform(
//                        get("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("fail-deletionDetail-member-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        ),
//                        responseFields(
//                                fieldWithPath("errCode").description("The error code"),
//                                fieldWithPath("errMsg").description("The error message")
//                        )));
//    }
//
//    @Test
//    @DisplayName("탈퇴 -> 노출대기 변경 - 성공")
//    public void deleteToConfirm_success() throws Exception {
//
//        // given
//        String res = "Enter state is changed into WAIT";
//
//        when(terminationService.deleteToConfirm(1L)).thenReturn(res);
//
//        // when
//        mockMvc.perform(
//                        patch("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("success-deleteToConfirm",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        )));
//    }
//
//    @Test
//    @DisplayName("탈퇴 -> 노출대기 변경 - 가게 찾지 못하는 에러")
//    public void deleteToConfirm_cannot_find_store() throws Exception {
//
//        when(terminationService.deleteToConfirm(1L)).thenThrow(new StoreException(NO_MATCH_STORE));
//
//        // when
//        mockMvc.perform(
//                        patch("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("fail-deleteToConfirm-store-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        ),
//                        responseFields(
//                                fieldWithPath("errCode").description("The error code"),
//                                fieldWithPath("errMsg").description("The error message")
//                        )));
//    }
//
//    @Test
//    @DisplayName("탈퇴 -> 노출대기 변경 - 회원 찾지 못하는 에러")
//    public void deleteToConfirm_cannot_find_member() throws Exception {
//
//        when(terminationService.deleteToConfirm(1L)).thenThrow(new MemberException(NOT_FOUND_MEMBER));
//
//        // when
//        mockMvc.perform(
//                        patch("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("fail-deleteToConfirm-member-err",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        ),
//                        responseFields(
//                                fieldWithPath("errCode").description("The error code"),
//                                fieldWithPath("errMsg").description("The error message")
//                        )));
//    }
//
//    @Test
//    @DisplayName("탈퇴 승인 - 성공")
//    public void success_confirmDelete() throws Exception {
//
//        // given
//        String res = "delete completed";
//
//        when(terminationService.confirmDelete(1L)).thenReturn(res);
//
//        // when
//        mockMvc.perform(
//                        delete("/delete/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken))
//                // then
//                .andExpect(status().isOk())
//                .andDo(document("success-confirmDelete",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("가게 unique id")
//                        )));
//    }
//
//    /**
//     * 시간 포매팅
//     */
//    private String timeSetter() {
//
//        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        String formattedOrderTime = nowTime.format(formatter);
//
//        return formattedOrderTime;
//    }
//}
