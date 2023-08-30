package com.zupzup.untact.service;

import com.zupzup.untact.documents.utils.RestDocsConfig;
import com.zupzup.untact.exception.MemberException;
import com.zupzup.untact.exception.MemberExceptionType;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.impl.MemberServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
@Import(RestDocsConfig.class)
@ExtendWith({RestDocumentationExtension.class})
@ContextConfiguration(classes = {TestConfiguration.class})
@AutoConfigureMockMvc
public class MemberServiceTest {

    @Autowired
    MemberServiceImpl loginService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("아이디 중복 조회 - 600 에러(중복 아이디) 발생")
    public void err_600_check_loginId() throws Exception {

        // given
        MemberReq memberReq = new MemberReq();
        memberReq.setLoginId("test");
        memberReq.setLoginPwd("test");
        memberReq.setEmail("test");

        given(memberRepository.findByLoginId(any(String.class))).willReturn(Optional.of(memberReq));

        // when
        Throwable exception = assertThrows(MemberException.class, () -> {
            loginService.checkLoginId("test");
        });

        // then
        assertEquals(MemberExceptionType.ALREADY_EXIST_USERNAME, ((MemberException) exception).getExceptionType());
    }
}
