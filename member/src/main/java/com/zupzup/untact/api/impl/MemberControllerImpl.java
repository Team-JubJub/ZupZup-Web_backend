package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.api.MemberController;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberCheckDuplicateLoginIdReq;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseService;
import com.zupzup.untact.service.impl.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberControllerImpl extends BaseControllerImpl<Member, MemberReq, MemberRes, MemberRepository> implements MemberController {

    public MemberControllerImpl(BaseService<Member, MemberReq, MemberRes, MemberRepository> baseService, MemberServiceImpl memberService) {
        super(baseService);
        this.memberService = memberService;
    }

    private final MemberServiceImpl memberService;

    /**
     * 중복 아이디 체크
     */
    @Override
    @PostMapping("/check")
    public ResponseEntity checkDuplicateLoginId(@RequestBody MemberCheckDuplicateLoginIdReq rq) {
        String rs = memberService.checkDuplicateLoginId(rq.getLoginId());

        return new ResponseEntity(rs, HttpStatus.OK);
    }
}
