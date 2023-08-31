package com.zupzup.untact.api;

import com.zupzup.untact.model.request.MemberReq;
import com.zupzup.untact.model.response.MemberRes;

public interface MemberController extends BaseController<MemberReq, MemberRes> {

    String checkLoginId(String loginId);
}
