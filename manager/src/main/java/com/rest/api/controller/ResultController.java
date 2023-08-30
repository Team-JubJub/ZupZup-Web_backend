package com.rest.api.controller;

import com.rest.api.model.dto.request.AcceptationRequest;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.ResultResponse;
import com.zupzup.untact.controller.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ResultController extends BaseController<ResultRequest, ResultResponse> {

    ResponseEntity decideAcceptation(@PathVariable Long id, @RequestBody AcceptationRequest result);
}
