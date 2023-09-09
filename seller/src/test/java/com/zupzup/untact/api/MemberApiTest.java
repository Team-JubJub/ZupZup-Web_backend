package com.zupzup.untact.api;

import com.zupzup.untact.api.impl.MemberControllerImpl;
import com.zupzup.untact.documents.RestDocsConfig;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({RestDocsConfig.class})
@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureMockMvc
public class MemberApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    private final String url = "/member";

    @BeforeEach
    public void before(WebApplicationContext ctx, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("사장님 생성/회원가입(C) - 성공")
    public void success_save() throws Exception {

        // given
        // response 생성
        MemberRes rs = new MemberRes();
        rs.setId(1L);
        rs.setLoginId("test");
        rs.setCreated_at(LocalDateTime.now());

        given(memberService.save(any(MemberReq.class))).will((Answer<MemberRes>) invocation -> {

            // memberService 에서 save 메소드 통과시에 id 추가
            rs.setId(1L);
            return rs;
        });

        // when
        mockMvc.perform(
                // POST 요청
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                "{"
                                + " \"name\": \"test\", "
                                + " \"phoneNum\": \"010-1111-1111\", "
                                + " \"ad\": false, "
                                + " \"loginId\": \"test\", "
                                + " \"loginPwd1\": \"pwd1\", "
                                + " \"loginPwd2\": \"pwd1\", "
                                + " \"email\": \"email\" "
                                + "}"
                        )
        )
        // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("loginId").value("test"))
                .andExpect(jsonPath("created_at").isNotEmpty());
    }
}
