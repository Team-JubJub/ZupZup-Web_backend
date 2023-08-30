package com.rest.api.service;

import com.rest.api.model.Result;
import com.rest.api.model.dto.request.AcceptationRequest;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.AcceptationResponse;
import com.rest.api.model.dto.response.ResultResponse;
import com.rest.api.repository.ResultRepository;
import com.zupzup.untact.service.BaseService;

public interface ResultService extends BaseService<Result, ResultRequest, ResultResponse, ResultRepository> {

    // 가게 승인/거절 api
    public AcceptationResponse decideAcceptation(Long id, AcceptationRequest result);
}
