package com.zupzup.untact.api;

import com.zupzup.untact.model.request.MemberPwdReq;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface MemberController extends BaseController<MemberReq, MemberRes> {

    ResponseEntity<String> checkLoginId(String loginId); // 중복 아이디 확인
    ResponseEntity<String> changePwd(@PathVariable Long id, MemberPwdReq rq); // 비밀번호 변
}
