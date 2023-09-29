package com.zupzup.untact.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zupzup.untact.documents.RestDocsConfig;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.model.dto.request.EnterUpdateReq;
import com.zupzup.untact.model.dto.request.StateReq;
import com.zupzup.untact.model.dto.response.EnterRes;
import com.zupzup.untact.model.dto.response.StoreRes;
import com.zupzup.untact.service.impl.ResultServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import({RestDocsConfig.class})
@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureMockMvc
public class ResultApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResultServiceImpl resultService;

    private final String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MjIiLCJyb2xlIjpbeyJuYW1lIjoiUk9MRV9NQU5BR0VSIn1dLCJpYXQiOjE2OTU5OTUzOTcsImV4cCI6MTY5NTk5ODk5N30.TPyRhIGJjZc6I3gOsnwQ7DRKH14RKiW1xrOogmiR_xk";

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("신규 신청 매장 상세 - 성공")
    public void success_new_detail() throws Exception {

        // given
        EnterRes rs = new EnterRes();
        rs.setId(1L);
        rs.setName("test name");
        rs.setMemberLoginId("test login id");
        rs.setPhoneNum("010-0000-0000");
        rs.setStoreNum("051-111-1111");
        rs.setCrNumber("crNum test");
        rs.setStoreName("test store name");
        rs.setStoreAddress("test address");

        when(resultService.enterDetail(1L)).thenReturn(rs);

        // when
        mockMvc.perform(
                get("/new/{id}", 1L)
                        .header("Authorization", "Bearer " + bearerToken)
        )// then
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("test name"))
                .andDo(document(
                        "success-new-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("unique id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("판매자 이름"),
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("판매자 로그인 아이디"),
                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("판매자 전화번호"),
                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("가게 전화번호"),
                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 번호"),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소")
                        )
                ));
    }

    @Test
    @DisplayName("신규 신청 매장 상세 - 실패")
    public void fail_new_detail() throws Exception {

        // given
        // 신규 신청 매장이 아닐 경우
        EnterRes rs = new EnterRes();
        rs.setName("신규 신청 매장이 아닙니다.");
        rs.setMemberLoginId(EnterState.WAIT.toString());

        given(resultService.enterDetail(1L)).willReturn(rs);

        // when
        mockMvc.perform(
                        get("/new/{id}", 1L)
                                .header("Authorization", "Bearer " + bearerToken)
                )// then
                .andExpect(status().isOk())
                .andExpect(jsonPath("memberLoginId").value(EnterState.WAIT.toString()))
                .andExpect(jsonPath("name").value("신규 신청 매장이 아닙니다."))
                .andDo(document(
                        "fail-new-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("null 값으로 전달"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("실패 이유 설명"),
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("어떤 state 에 있는지 확인"),
                                fieldWithPath("phoneNum").type(JsonFieldType.NULL).description("null 값으로 전달"),
                                fieldWithPath("storeNum").type(JsonFieldType.NULL).description("null 값으로 전달"),
                                fieldWithPath("crNumber").type(JsonFieldType.NULL).description("null 값으로 전달"),
                                fieldWithPath("storeName").type(JsonFieldType.NULL).description("null 값으로 전달"),
                                fieldWithPath("storeAddress").type(JsonFieldType.NULL).description("null 값으로 전달")
                        )
                ));
    }

    @Test
    @DisplayName("신규 매장 노출 대기로 변경 - 성공")
    public void success_new_to_wait() throws Exception {

        // given
        StateReq rq = new StateReq();
        rq.setId(1L);
        rq.setIsAccepted(true);

        when(resultService.newToWait(any(StateReq.class))).thenReturn("Enter state is changed into WAIT");

        // json parse
        String jsonRq = objectMapper.writeValueAsString(rq);

        // when
        mockMvc.perform(
                post("/new")
                        .header("Authorization", "Bearer " + bearerToken)
                        .content(jsonRq)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                // then
                .andExpect(content().string("Enter state is changed into WAIT"))
                .andExpect(status().isOk())
                .andDo(document(
                        "success-new-to-wait",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
                        )
                ));
    }

    @Test
    @DisplayName("신규 매장 노출 대기로 변경 - 실패 (isAccepted 가 false 인 경우)")
    public void fail_new_to_wait() throws Exception {
        // given
        StateReq rq = new StateReq();
        rq.setId(1L);
        rq.setIsAccepted(false);

        when(resultService.newToWait(any(StateReq.class))).thenReturn("Cannot find request");

        // json parse
        String jsonRq = objectMapper.writeValueAsString(rq);

        // when
        mockMvc.perform(
                        post("/new")
                                .header("Authorization", "Bearer " + bearerToken)
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                // then
                .andExpect(content().string("Cannot find request"))
                .andExpect(status().isOk())
                .andDo(document(
                        "fail-new-to-wait",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
                        )
                ));
    }

    @Test
    @DisplayName("신청서 수정 - 성공")
    public void success_update_enter() throws Exception {

        // given
        // request 생성
        EnterUpdateReq rq = new EnterUpdateReq();
        rq.setStoreNum("051-000-0000");
        rq.setStoreName("test update store name");
        rq.setStoreAddress("test update address");

        String jsonRq = objectMapper.writeValueAsString(rq);

        // response 생성
        EnterRes rs = new EnterRes();
        rs.setId(1L);
        rs.setName("test name");
        rs.setMemberLoginId("test login id");
        rs.setPhoneNum("010-0000-0000");
        rs.setStoreNum("051-000-0000");
        rs.setCrNumber("crNum test");
        rs.setStoreName("test update store name");
        rs.setStoreAddress("test update address");

        when(resultService.updateEnterDetail(anyLong(), any(EnterUpdateReq.class))).thenReturn(rs);

        // when
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/new/{id}", 1L)
                                .header("Authorization", "Bearer " + bearerToken)
                                .content(jsonRq)
                                .contentType(MediaType.APPLICATION_JSON)
                )// then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeName").value("test update store name"))
                .andExpect(jsonPath("storeNum").value("051-000-0000"))
                .andDo(document(
                        "success-update-enter",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("(수정된) 가게 전화번호"),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("(수정된) 가게 이름"),
                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("(수정된) 가게 주소")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("enter unique id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("판매자 이름"),
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("판매자 로그인 id"),
                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("판매자 전화번호"),
                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("가게 전화번호"),
                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소")
                        )
                ));
    }

    @Test
    @DisplayName("노출 대기 매장 상세 - 성공")
    public void success_wait_detail() throws Exception {

        // given
        StoreRes rs = new StoreRes();
        rs.setStoreId(1L);
        rs.setSellerName("seller name");
        rs.setSellerLoginId("seller Login Id");
        rs.setStoreContact("store contact");
        rs.setCrNumber("crNumber");
        rs.setStoreAddress("store address");
        rs.setStoreImageUrl("store Image url");
        rs.setCategory("category");

        when(resultService.storeDetail(anyLong())).thenReturn(rs);

        // when
        mockMvc.perform(
                        get("/wait/{id}", 1L)
                                .header("Authorization", "Bearer " + bearerToken)
                )// then
                .andExpect(status().isOk())
                .andExpect(jsonPath("storeId").value(1L))
                .andExpect(jsonPath("sellerName").value("seller name"))
                .andDo(document(
                        "success-wait-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("Store unique Id"),
                                fieldWithPath("sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
                                fieldWithPath("sellerLoginId").type(JsonFieldType.STRING).description("판매자 로그인 아이디"),
                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("가게 전화번호"),
                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
                                fieldWithPath("storeImageUrl").type(JsonFieldType.STRING).description("가게 이미지"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("가게 카테고리")
                        )
                ));
    }
}
