package com.zupzup.untact.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zupzup.untact.documents.RestDocsConfig;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.model.dto.response.EnterRes;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                                fieldWithPath("name").type(JsonFieldType.STRING).description("사장님 이름"),
                                fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("사장님 로그인 아이디"),
                                fieldWithPath("phoneNum").type(JsonFieldType.STRING).description("사장님 전화번호"),
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


    }
}
