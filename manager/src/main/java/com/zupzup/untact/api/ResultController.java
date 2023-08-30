package com.zupzup.untact.api;

import com.zupzup.untact.model.dto.request.AcceptationRequest;
import com.zupzup.untact.model.dto.request.ResultRequest;
import com.zupzup.untact.model.dto.response.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ResultController extends BaseController<ResultRequest, ResultResponse> {

    ResponseEntity decideAcceptation(@PathVariable Long id, @RequestBody AcceptationRequest result);
}
