//package com.zupzup.untact.api;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.zupzup.untact.documents.RestDocsConfig;
//import com.zupzup.untact.domain.enums.EnterState;
//import com.zupzup.untact.exception.ManagerException;
//import com.zupzup.untact.exception.enter.EnterException;
//import com.zupzup.untact.exception.member.MemberException;
//import com.zupzup.untact.model.dto.request.EnterUpdateReq;
//import com.zupzup.untact.model.dto.request.StateReq;
//import com.zupzup.untact.model.dto.request.StoreUpdateReq;
//import com.zupzup.untact.model.dto.response.*;
//import com.zupzup.untact.service.impl.ResultServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.restdocs.request.RequestDocumentation;
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
//import static com.zupzup.untact.exception.ManagerExceptionType.EMPTY_LIST;
//import static com.zupzup.untact.exception.enter.EnterExceptionType.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@Import({RestDocsConfig.class})
//@ExtendWith({RestDocumentationExtension.class})
//@AutoConfigureMockMvc
//public class ResultApiTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private ResultServiceImpl resultService;
//
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
//    /**
//     * NEW
//     */
//    @Test
//    @DisplayName("신규 신청 매장 전체 보기 - 성공")
//    public void success_new_list() throws Exception {
//
//        // given
//        List<EnterListRes> eList = new ArrayList<>();
//
//        EnterListRes eRes = new EnterListRes();
//        eRes.setId(1L);
//        eRes.setName("test name 1");
//        eRes.setStoreName("test store name 1");
//        eRes.setCreated_at(timeSetter());
//        eList.add(eRes);
//
//        EnterListRes eRes1 = new EnterListRes();
//        eRes1.setId(2L);
//        eRes1.setName("test name 2");
//        eRes1.setStoreName("test store name 2");
//        eRes1.setCreated_at(timeSetter());
//        eList.add(eRes1);
//
//        EnterListRes eRes2 = new EnterListRes();
//        eRes2.setId(3L);
//        eRes2.setName("test name 3");
//        eRes2.setStoreName("test store name 3");
//        eRes2.setCreated_at(timeSetter());
//        eList.add(eRes2);
//
//        EnterListRes eRes3 = new EnterListRes();
//        eRes3.setId(4L);
//        eRes3.setName("test name 4");
//        eRes3.setStoreName("test store name 4");
//        eRes3.setCreated_at(timeSetter());
//        eList.add(eRes3);
//
//        when(resultService.enterList()).thenReturn(eList);
//
//        // when
//        mockMvc.perform(
//                get("/new")
//                        .header("Authorization", "Bearer " + bearerToken)
//        )
//        // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-new-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("enter unique ID"),
//                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].created_at").type(JsonFieldType.STRING).description("생성된 시간")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("신규 신청 매장 전체 보기 - 값이 없는 경우")
//    public void fail_new_list() throws Exception {
//
//        // given
//        when(resultService.enterList()).thenReturn(new ArrayList<>());
//
//        // when
//        mockMvc.perform(
//                get("/new")
//                        .header("Authorization", "Bearer " + bearerToken)
//        )
//                // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("[]"))
//                .andDo(document(
//                        "none-data-new-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[]").description("빈 리스트 전달")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("신규 신청 매장 상세 - 성공")
//    public void success_new_detail() throws Exception {
//
//        // given
//        EnterRes rs = new EnterRes();
//        rs.setId(1L);
//        rs.setName("test name");
//        rs.setMemberLoginId("test login id");
//        rs.setPhoneNum("010-0000-0000");
//        rs.setStoreNum("051-111-1111");
//        rs.setCrNumber("crNum test");
//        rs.setStoreName("test store name");
//        rs.setStoreAddress("test address");
//
//        when(resultService.enterDetail(1L)).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                get("/new/{id}", 1L)
//                        .header("Authorization", "Bearer " + bearerToken)
//        )// then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1L))
//                .andExpect(jsonPath("name").value("test name"))
//                .andDo(document(
//                        "success-new-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("unique id"),
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("판매자 로그인 아이디"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("판매자 전화번호"),
//                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 신청 매장 상세 - 실패")
//    public void fail_new_detail() throws Exception {
//
//        // given
//        given(resultService.enterDetail(1L)).willThrow(new EnterException(ENTER_STATE_IS_NOT_NEW));
//
//        // when
//        mockMvc.perform(
//                        get("/new/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-new-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 매장 노출 대기로 변경 - 성공")
//    public void success_new_to_wait() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(true);
//
//        when(resultService.newToWait(any(StateReq.class))).thenReturn("Enter state is changed into WAIT");
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                post("/new")
//                        .header("Authorization", "Bearer " + bearerToken)
//                        .content(jsonRq)
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                // then
//                .andExpect(content().string("Enter state is changed into WAIT"))
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-new-to-wait",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 매장 노출 대기로 변경 - 실패 (가게를 찾지 못함)")
//    public void fail_new_to_wait_cannot_find_store() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.newToWait(any(StateReq.class))).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/new")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-new-to-wait-cannot-find-store",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 매장 노출 대기로 변경 - 실패 (isAccepted 가 false 인 경우)")
//    public void fail_new_to_wait() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.newToWait(any(StateReq.class))).thenThrow(new EnterException(IS_ACCEPTED_FALSE));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/new")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-new-to-wait",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 매장 노출 대기로 변경 - 실패 (가게의 상태가 NEW 인지 확인)")
//    public void fail_new_to_wait_state_is_not_new() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.newToWait(any(StateReq.class))).thenThrow(new EnterException(ENTER_STATE_IS_NOT_NEW));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/new")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-new-to-wait-enter-state-is-not-new",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신청서 삭제 - 성공")
//    public void success_delete_enter() throws Exception {
//
//        // given
//        when(resultService.deleteEnter(anyLong())).thenReturn("Enter is deleted");
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.put("/new/{id}", 1L)
//                        .header("Authorization", "Bearer " + bearerToken)
//        )
//        // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("Enter is deleted"))
//                .andDo(document(
//                        "success-delete-enter",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("Enter unique id")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신청서 삭제 - 실패 (해당 Id 가게를 찾지 못함)")
//    public void fail_delete_enter() throws Exception {
//
//        // given
//        when(resultService.deleteEnter(1L)).thenThrow(new MemberException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.put("/new/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-delete-enter",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("Enter unique id")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신청서 수정 - 성공")
//    public void success_update_enter() throws Exception {
//
//        // given
//        // request 생성
//        EnterUpdateReq rq = new EnterUpdateReq();
//        rq.setStoreNum("051-000-0000");
//        rq.setStoreName("test update store name");
//        rq.setStoreAddress("test update address");
//
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // response 생성
//        EnterRes rs = new EnterRes();
//        rs.setId(1L);
//        rs.setName("test name");
//        rs.setMemberLoginId("test login id");
//        rs.setPhoneNum("010-0000-0000");
//        rs.setStoreNum("051-000-0000");
//        rs.setCrNumber("crNum test");
//        rs.setStoreName("test update store name");
//        rs.setStoreAddress("test update address");
//
//        when(resultService.updateEnterDetail(anyLong(), any(EnterUpdateReq.class))).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.post("/new/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )// then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("storeName").value("test update store name"))
//                .andExpect(jsonPath("storeNum").value("051-000-0000"))
//                .andDo(document(
//                        "success-update-enter",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("(수정된) 가게 전화번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("(수정된) 가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("(수정된) 가게 주소")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("enter unique id"),
//                                fieldWithPath("name").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("판매자 로그인 id"),
//                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("판매자 전화번호"),
//                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신청서 수정 - 실패 (해당 Id 가게를 찾지 못함)")
//    public void fail_update_enter_detail() throws Exception {
//
//        // given
//        EnterUpdateReq enterUpdateReq = new EnterUpdateReq();
//        enterUpdateReq.setStoreNum("051-000-0000");
//        enterUpdateReq.setStoreName("test update store name");
//        enterUpdateReq.setStoreAddress("test update address");
//
//        String jsonRq = objectMapper.writeValueAsString(enterUpdateReq);
//
//        when(resultService.updateEnterDetail(anyLong(), any(EnterUpdateReq.class))).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.post("/new/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-enter-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("Enter unique id")
//                        ),
//                        requestFields(
//                                fieldWithPath("storeNum").type(JsonFieldType.STRING).description("(수정된) 가게 전화번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("(수정된) 가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("(수정된) 가게 주소")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("신규 신청 매장 검색 - 성공")
//    public void success_new_search() throws Exception {
//
//        // given
//        List<EnterListRes> eList = new ArrayList<>();
//
//        EnterListRes eRes = new EnterListRes();
//        eRes.setId(1L);
//        eRes.setName("test name 1");
//        eRes.setStoreName("test store name 1");
//        eRes.setCreated_at(timeSetter());
//        eList.add(eRes);
//
//        EnterListRes eRes1 = new EnterListRes();
//        eRes1.setId(2L);
//        eRes1.setName("test name 2");
//        eRes1.setStoreName("test store name 2");
//        eRes1.setCreated_at(timeSetter());
//        eList.add(eRes1);
//
//        when(resultService.searchEnterList(any(String.class))).thenReturn(eList);
//
//        // when
//        mockMvc.perform(
//                get("/new/search")
//                        .header("Authorization", "Bearer " + bearerToken)
//                        .param("keyword", "test")
//        )
//        // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-new-search",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        RequestDocumentation.queryParameters(
//                                RequestDocumentation.parameterWithName("keyword").description("검색 키워드")
//                        ),
//                        responseFields(
//                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("store unique ID"),
//                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].created_at").type(JsonFieldType.STRING).description("변경된 시간")
//                        ))
//                );
//    }
//
//    /**
//     * wait
//     */
//    @Test
//    @DisplayName("노출 대기 매장 전체 보기 - 성공")
//    public void success_wait_list() throws Exception {
//        // given
//        List<WaitStoreListRes> sList = new ArrayList<>();
//
//        WaitStoreListRes wRes = new WaitStoreListRes();
//        wRes.setStoreId(1L);
//        wRes.setSellerName("test name 1");
//        wRes.setStoreName("test store name 1");
//        wRes.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes);
//
//        WaitStoreListRes wRes1 = new WaitStoreListRes();
//        wRes1.setStoreId(2L);
//        wRes1.setSellerName("test name 2");
//        wRes1.setStoreName("test store name 2");
//        wRes1.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes1);
//
//        WaitStoreListRes wRes2 = new WaitStoreListRes();
//        wRes2.setStoreId(3L);
//        wRes2.setSellerName("test name 3");
//        wRes2.setStoreName("test store name 3");
//        wRes2.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes2);
//
//        WaitStoreListRes wRes3 = new WaitStoreListRes();
//        wRes3.setStoreId(4L);
//        wRes3.setSellerName("test name 4");
//        wRes3.setStoreName("test store name 4");
//        wRes3.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes3);
//
//        when(resultService.waitStoreList()).thenReturn(sList);
//
//        // when
//        mockMvc.perform(
//                        get("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-wait-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[].storeId").type(JsonFieldType.NUMBER).description("store unique ID"),
//                                fieldWithPath("[].sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].waitStatusTimestamp").type(JsonFieldType.STRING).description("변경된 시간")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 전체 보기 - 데이터 없음")
//    public void no_data_wait_list() throws Exception {
//
//        // given
//        when(resultService.waitStoreList()).thenReturn(new ArrayList<>());
//
//        // when
//        mockMvc.perform(
//                        get("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("[]"))
//                .andDo(document(
//                        "none-data-wait-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[]").description("빈 리스트 전달")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 상세 - 성공")
//    public void success_wait_detail() throws Exception {
//
//        // given
//        StoreRes rs = new StoreRes();
//        rs.setStoreId(1L);
//        rs.setSellerName("seller name");
//        rs.setSellerLoginId("seller Login Id");
//        rs.setSellerContact("010-1111-1111");
//        rs.setStoreContact("store contact");
//        rs.setCrNumber("crNumber");
//        rs.setStoreName("store name");
//        rs.setStoreAddress("store address");
//        rs.setStoreImageUrl("store Image url");
//        rs.setCategory("CAFE");
//
//        when(resultService.storeDetail(anyLong())).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                        get("/wait/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("storeId").value(1L))
//                .andExpect(jsonPath("sellerName").value("seller name"))
//                .andDo(document(
//                        "success-wait-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("Store unique Id"),
//                                fieldWithPath("sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("sellerLoginId").type(JsonFieldType.STRING).description("판매자 로그인 아이디"),
//                                fieldWithPath("sellerContact").type(JsonFieldType.STRING).description("판매자 전화번호"),
//                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                fieldWithPath("storeImageUrl").type(JsonFieldType.STRING).description("가게 이미지"),
//                                fieldWithPath("category").type(JsonFieldType.STRING).description("가게 카테고리")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 상세 - 실패")
//    public void fail_wait_detail() throws Exception {
//
//        // given
//        when(resultService.storeDetail(1L)).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.get("/wait/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-wait-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(
//                                parameterWithName("id").description("Store unique id")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 삭제 - 성공")
//    public void success_delete_store() throws Exception {
//
//        // given
//        when(resultService.deleteStore(anyLong())).thenReturn("Delete completed");
//
//        // when
//        mockMvc.perform(
//                delete("/wait/{id}", 1L)
//                        .header("Authorization", "Bearer " + bearerToken)
//        )
//        // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("Delete completed"))
//                .andDo(
//                        document(
//                                "success-wait-delete",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint())
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 삭제 - 실패")
//    public void fail_delete_store() throws Exception {
//
//        // given
//        when(resultService.deleteStore(anyLong())).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        delete("/wait/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(
//                        document(
//                                "fail-wait-delete",
//                                preprocessRequest(prettyPrint()),
//                                preprocessResponse(prettyPrint())
//                        )
//                );
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 노출 승인으로 변경 - 성공")
//    public void success_wait_to_confirm() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(true);
//
//        when(resultService.waitToConfirm(any(StateReq.class))).thenReturn("Enter state is changed into CONFIRM");
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(content().string("Enter state is changed into CONFIRM"))
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-wait-to-confirm",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 노출 승인으로 변경 - 실패 (가게를 찾지 못함)")
//    public void fail_wait_to_confirm_empty_list() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.waitToConfirm(any(StateReq.class))).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-wait-to-confirm-empty-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 노출 승인으로 변경 - 실패 (isAccepted false)")
//    public void fail_wait_to_confirm_isAccepted_false() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.waitToConfirm(any(StateReq.class))).thenThrow(new EnterException(IS_ACCEPTED_FALSE));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-wait-to-confirm-is-accepted-false",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 노출 승인으로 변경 - 실패 (Enterstate wait 아님)")
//    public void fail_wait_to_confirm_enterstate_is_not_wait() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.waitToConfirm(any(StateReq.class))).thenThrow(new EnterException(ENTER_STATE_IS_NOT_WAIT));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/wait")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-wait-to-confirm-enterstate-is-not-wait",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 수정 - 성공")
//    public void success_update_store() throws Exception {
//
//        // given
//        // request 생성
//        StoreUpdateReq rq = new StoreUpdateReq();
//        rq.setStoreContact("051-000-0000");
//        rq.setStoreName("test update store name");
//        rq.setStoreAddress("test update address");
//        rq.setCategory("CAFE");
//
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // response 생성
//        StoreRes rs = new StoreRes();
//        rs.setStoreId(1L);
//        rs.setSellerName("seller name");
//        rs.setSellerLoginId("seller Login Id");
//        rs.setSellerContact("010-1111-1111");
//        rs.setStoreContact("051-000-0000");
//        rs.setCrNumber("crNumber");
//        rs.setStoreName("test update store name");
//        rs.setStoreAddress("test update address");
//        rs.setStoreImageUrl("store Image url");
//        rs.setCategory("CAFE");
//
//        when(resultService.updateStoreDetail(anyLong(), any(StoreUpdateReq.class))).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.post("/wait/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )// then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("storeName").value("test update store name"))
//                .andExpect(jsonPath("storeContact").value("051-000-0000"))
//                .andDo(document(
//                        "success-update-store",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("(수정된) 가게 전화번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("(수정된) 가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("(수정된) 가게 주소"),
//                                fieldWithPath("category").type(JsonFieldType.STRING).description("(수정된) 가게 카테고리")
//                        ),
//                        responseFields(
//                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("store unique id"),
//                                fieldWithPath("sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("sellerLoginId").type(JsonFieldType.STRING).description("판매자 로그인 id"),
//                                fieldWithPath("sellerContact").type(JsonFieldType.STRING).description("판매자 전화번호"),
//                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                fieldWithPath("storeImageUrl").type(JsonFieldType.STRING).description("가게 이미지 url"),
//                                fieldWithPath("category").type(JsonFieldType.STRING).description("가게 카테고리")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 대기 매장 수정 - 실패(가게없음)")
//    public void fail_update_store_detail_empty_list() throws Exception {
//
//        // given
//        // request 생성
//        StoreUpdateReq rq = new StoreUpdateReq();
//        rq.setStoreContact("051-000-0000");
//        rq.setStoreName("test update store name");
//        rq.setStoreAddress("test update address");
//        rq.setCategory("CAFE");
//
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        when(resultService.updateStoreDetail(anyLong(), any(StoreUpdateReq.class))).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        RestDocumentationRequestBuilders.post("/wait/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )// then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-update-store-empty-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("(수정된) 가게 전화번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("(수정된) 가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("(수정된) 가게 주소"),
//                                fieldWithPath("category").type(JsonFieldType.STRING).description("(수정된) 가게 카테고리")
//                        )
//                ));
//    }
//
//        @Test
//    @DisplayName("노출 대기 매장 검색 - 성공")
//    public void success_wait_search() throws Exception {
//
//        // given
//        List<WaitStoreListRes> sList = new ArrayList<>();
//
//        WaitStoreListRes wRes = new WaitStoreListRes();
//        wRes.setStoreId(1L);
//        wRes.setSellerName("test name 1");
//        wRes.setStoreName("test store name 1");
//        wRes.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes);
//
//        WaitStoreListRes wRes1 = new WaitStoreListRes();
//        wRes1.setStoreId(2L);
//        wRes1.setSellerName("test name 2");
//        wRes1.setStoreName("test store name 2");
//        wRes1.setWaitStatusTimestamp(timeSetter());
//        sList.add(wRes1);
//
//        when(resultService.searchWaitStoreList(any(String.class))).thenReturn(sList);
//
//        // when
//        mockMvc.perform(
//                        get("/wait/search")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .param("keyword", "test")
//                )
//                // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-wait-search",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        RequestDocumentation.queryParameters(
//                                RequestDocumentation.parameterWithName("keyword").description("검색 키워드")
//                        ),
//                        responseFields(
//                                fieldWithPath("[].storeId").type(JsonFieldType.NUMBER).description("store unique ID"),
//                                fieldWithPath("[].sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].waitStatusTimestamp").type(JsonFieldType.STRING).description("변경된 시간")
//                        ))
//                );
//    }
//
//    /**
//     * confirm
//     */
//    @Test
//    @DisplayName("노출 승인 매장 전체 보기 - 성공")
//    public void success_confirm_list() throws Exception {
//        // given
//        List<ConfirmStoreListRes> sList = new ArrayList<>();
//
//        ConfirmStoreListRes cRes = new ConfirmStoreListRes();
//        cRes.setStoreId(1L);
//        cRes.setSellerName("test name 1");
//        cRes.setStoreName("test store name 1");
//        cRes.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes);
//
//        ConfirmStoreListRes cRes1 = new ConfirmStoreListRes();
//        cRes1.setStoreId(2L);
//        cRes1.setSellerName("test name 2");
//        cRes1.setStoreName("test store name 2");
//        cRes1.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes1);
//
//        ConfirmStoreListRes cRes2 = new ConfirmStoreListRes();
//        cRes2.setStoreId(3L);
//        cRes2.setSellerName("test name 3");
//        cRes2.setStoreName("test store name 3");
//        cRes2.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes2);
//
//        ConfirmStoreListRes cRes3 = new ConfirmStoreListRes();
//        cRes3.setStoreId(4L);
//        cRes3.setSellerName("test name 4");
//        cRes3.setStoreName("test store name 4");
//        cRes3.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes3);
//
//        when(resultService.confirmStoreList()).thenReturn(sList);
//
//        // when
//        mockMvc.perform(
//                        get("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-confirm-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[].storeId").type(JsonFieldType.NUMBER).description("store unique ID"),
//                                fieldWithPath("[].sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].confirmStatusTimestamp").type(JsonFieldType.STRING).description("변경된 시간")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 전체보기 - 데이터 없음")
//    public void none_data_confirm_list() throws Exception {
//
//        // given
//        when(resultService.confirmStoreList()).thenReturn(new ArrayList<>());
//
//        // when
//        mockMvc.perform(
//                        get("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                )
//                // then
//                .andExpect(status().isOk())
//                .andExpect(content().string("[]"))
//                .andDo(document(
//                        "none-data-confirm-list",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("[]").description("빈 리스트 전달")
//                        ))
//                );
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 상세보기 - 성공")
//    public void success_confirm_detail() throws Exception {
//
//        // given
//        StoreRes rs = new StoreRes();
//        rs.setStoreId(1L);
//        rs.setSellerName("seller name");
//        rs.setSellerLoginId("seller Login Id");
//        rs.setSellerContact("010-1111-1111");
//        rs.setStoreContact("store contact");
//        rs.setCrNumber("crNumber");
//        rs.setStoreName("store name");
//        rs.setStoreAddress("store address");
//        rs.setStoreImageUrl("store Image url");
//        rs.setCategory("CAFE");
//
//        when(resultService.confirmStoreDetail(anyLong())).thenReturn(rs);
//
//        // when
//        mockMvc.perform(
//                        get("/confirm/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("storeId").value(1L))
//                .andExpect(jsonPath("sellerName").value("seller name"))
//                .andDo(document(
//                        "success-confirm-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        responseFields(
//                                fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("Store unique Id"),
//                                fieldWithPath("sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("sellerLoginId").type(JsonFieldType.STRING).description("판매자 로그인 아이디"),
//                                fieldWithPath("sellerContact").type(JsonFieldType.STRING).description("판매자 전화번호"),
//                                fieldWithPath("storeContact").type(JsonFieldType.STRING).description("가게 전화번호"),
//                                fieldWithPath("crNumber").type(JsonFieldType.STRING).description("사업자 등록증 번호"),
//                                fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("storeAddress").type(JsonFieldType.STRING).description("가게 주소"),
//                                fieldWithPath("storeImageUrl").type(JsonFieldType.STRING).description("가게 이미지"),
//                                fieldWithPath("category").type(JsonFieldType.STRING).description("가게 카테고리")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 상세보기 - 실패 (가게 Id 찾지 못함)")
//    public void fail_confirm_detail() throws Exception {
//
//        // given
//        when(resultService.confirmStoreDetail(1L)).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // when
//        mockMvc.perform(
//                        get("/confirm/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-confirm-detail",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 상세보기 - 실패 (Enterstate confirm 아님)")
//    public void fail_confirm_detail_enterstate_is_not_confirm() throws Exception {
//
//        // given
//        when(resultService.confirmStoreDetail(1L)).thenThrow(new EnterException(ENTER_STATE_IS_NOT_CONFIRM));
//
//        // when
//        mockMvc.perform(
//                        get("/confirm/{id}", 1L)
//                                .header("Authorization", "Bearer " + bearerToken)
//                )// then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-confirm-detail-enterstate-is-not-confirm",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint())
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 노출 대기로 변경 - 성공")
//    public void success_confirm_to_wait() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(true);
//
//        when(resultService.confirmToWait(any(StateReq.class))).thenReturn("Enter state is changed into WAIT");
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(content().string("Enter state is changed into WAIT"))
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-confirm-to-wait",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 노출 대기로 변경 - 실패 (가게 Id 없음)")
//    public void fail_confirm_to_wait_no_store_id() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.confirmToWait(any(StateReq.class))).thenThrow(new ManagerException(EMPTY_LIST));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-confirm-to-wait-no-store-id",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 노출 대기로 변경 - 실패 (isAccepted false)")
//    public void fail_confirm_to_wait_isAccepted_false() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.confirmToWait(any(StateReq.class))).thenThrow(new EnterException(IS_ACCEPTED_FALSE));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-confirm-to-wait-isAccepted_false",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("노출 승인 매장 노출 대기로 변경 - 실패 (Enterstate is not confirm)")
//    public void fail_confirm_to_wait_enterState_is_not_confirm() throws Exception {
//
//        // given
//        StateReq rq = new StateReq();
//        rq.setId(1L);
//        rq.setIsAccepted(false);
//
//        when(resultService.confirmToWait(any(StateReq.class))).thenThrow(new EnterException(ENTER_STATE_IS_NOT_CONFIRM));
//
//        // json parse
//        String jsonRq = objectMapper.writeValueAsString(rq);
//
//        // when
//        mockMvc.perform(
//                        post("/confirm")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .content(jsonRq)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                // then
//                .andExpect(status().isBadRequest())
//                .andDo(document(
//                        "fail-confirm-to-wait-enterState-is-not-confirm",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상태 변경하고자 하는 신청서 unique Id"),
//                                fieldWithPath("isAccepted").type(JsonFieldType.BOOLEAN).description("True 값을 전달하여 상태 변경임을 전달")
//                        )
//                ));
//    }
//
//        @Test
//    @DisplayName("노출 대기 매장 검색 - 성공")
//    public void success_confirm_search() throws Exception {
//
//        // given
//        List<ConfirmStoreListRes> sList = new ArrayList<>();
//
//        ConfirmStoreListRes cRes = new ConfirmStoreListRes();
//        cRes.setStoreId(1L);
//        cRes.setSellerName("test name 1");
//        cRes.setStoreName("test store name 1");
//        cRes.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes);
//
//        ConfirmStoreListRes cRes1 = new ConfirmStoreListRes();
//        cRes1.setStoreId(2L);
//        cRes1.setSellerName("test name 2");
//        cRes1.setStoreName("test store name 2");
//        cRes1.setConfirmStatusTimestamp(timeSetter());
//        sList.add(cRes1);
//
//        when(resultService.searchConfirmStoreList(any(String.class))).thenReturn(sList);
//
//        // when
//        mockMvc.perform(
//                        get("/confirm/search")
//                                .header("Authorization", "Bearer " + bearerToken)
//                                .param("keyword", "test")
//                )
//                // then
//                .andExpect(status().isOk())
//                .andDo(document(
//                        "success-confirm-search",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        RequestDocumentation.queryParameters(
//                                RequestDocumentation.parameterWithName("keyword").description("검색 키워드")
//                        ),
//                        responseFields(
//                                fieldWithPath("[].storeId").type(JsonFieldType.NUMBER).description("store unique ID"),
//                                fieldWithPath("[].sellerName").type(JsonFieldType.STRING).description("판매자 이름"),
//                                fieldWithPath("[].storeName").type(JsonFieldType.STRING).description("가게 이름"),
//                                fieldWithPath("[].confirmStatusTimestamp").type(JsonFieldType.STRING).description("변경된 시간")
//                        ))
//                );
//    }
//
//    //--------------------
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
//
//    /**
//     * 테스트시 쓰기 위한 에러 Response
//     */
//    static class TestExceptionRes {
//
//        private Integer errCode;
//        private String errMsg;
//
//        public TestExceptionRes(Integer errCode, String errMsg) {
//            this.errCode = errCode;
//            this.errMsg = errMsg;
//        }
//
//        public Integer getErrCode() {
//            return errCode;
//        }
//
//        public String getErrMsg() {
//            return errMsg;
//        }
//    }
//}
