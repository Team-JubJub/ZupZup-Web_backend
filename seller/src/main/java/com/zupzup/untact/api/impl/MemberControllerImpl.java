package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.api.MemberController;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseService;
import com.zupzup.untact.service.impl.MemberServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberControllerImpl extends BaseControllerImpl<Member, MemberReq, MemberRes, MemberRepository> implements MemberController {

    public MemberControllerImpl(BaseService<Member, MemberReq, MemberRes, MemberRepository> baseService, MemberServiceImpl loginService) {
        super(baseService);
        this.loginService = loginService;
    }

    private final MemberServiceImpl loginService;

    /**
     * 중복 아이디 체크
     */
    @Override
    @PostMapping("/check")
    public String checkLoginId(String loginId) {
        String res = loginService.checkLoginId(loginId);

        return res;
    }
}
