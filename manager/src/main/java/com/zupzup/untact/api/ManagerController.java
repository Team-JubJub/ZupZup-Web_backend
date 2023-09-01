package com.zupzup.untact.api;

import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.model.response.EnterApprovalRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ManagerController extends BaseController<ManagerReq, ManagerRes> {

    ResponseEntity<EnterApprovalRes> managerApproval(@PathVariable Long id, Boolean approval);
}
