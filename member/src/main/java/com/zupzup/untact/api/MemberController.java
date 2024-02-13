package com.zupzup.untact.api;

import com.zupzup.untact.model.request.MemberCheckDuplicateLoginIdReq;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import org.springframework.http.ResponseEntity;

public interface MemberController extends BaseController<MemberReq, MemberRes> {

    ResponseEntity<String> checkDuplicateLoginId(MemberCheckDuplicateLoginIdReq rq); // 중복 아이디 확인
}
