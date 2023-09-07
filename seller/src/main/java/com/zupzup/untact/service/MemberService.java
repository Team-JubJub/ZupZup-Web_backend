package com.zupzup.untact.service;

import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberFindReq;
import com.zupzup.untact.model.request.MemberPwdReq;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;

public interface MemberService extends BaseService<Member, MemberReq, MemberRes, MemberRepository> {

    String checkLoginId(String loginId); // 아이디 중복 조회
    String changePwd(Long id, MemberPwdReq rq); // 비밀번호 변경
    MemberRes findLoginId(MemberFindReq rq); // 아이디 찾기
}
