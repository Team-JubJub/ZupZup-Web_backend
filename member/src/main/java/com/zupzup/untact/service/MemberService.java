package com.zupzup.untact.service;

import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.repository.MemberRepository;

public interface MemberService extends BaseService<Member, MemberReq, MemberRes, MemberRepository> {

    String checkDuplicateLoginId(String loginId); // 아이디 중복 조회
}
