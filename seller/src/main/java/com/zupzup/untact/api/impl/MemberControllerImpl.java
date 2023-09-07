package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.api.MemberController;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberFindReq;
import com.zupzup.untact.model.request.MemberPwdReq;
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
    public ResponseEntity checkLoginId(String loginId) {
        String rs = memberService.checkLoginId(loginId);

        return new ResponseEntity(rs, HttpStatus.OK);
    }

    /**
     * 비밀번호 변경
     */
    @Override
    @PostMapping("/change/{id}")
    public ResponseEntity changePwd(@PathVariable Long id, @RequestBody MemberPwdReq rq) {

        String rs = memberService.changePwd(id, rq);

        return new ResponseEntity(rs, HttpStatus.OK);
    }

    /**
     * 아이디 찾기
     */
    @Override
    @PostMapping("/find")
    public ResponseEntity<MemberRes> findLoginId(@RequestBody MemberFindReq rq) {

        MemberRes rs = memberService.findLoginId(rq);
        return new ResponseEntity(rs, HttpStatus.OK);
    }
}
