package com.zupzup.untact.service;

import com.zupzup.untact.model.Result;
import com.zupzup.untact.model.dto.request.AcceptationRequest;
import com.zupzup.untact.model.dto.request.ResultRequest;
import com.zupzup.untact.model.dto.response.AcceptationResponse;
import com.zupzup.untact.model.dto.response.ResultResponse;
import com.zupzup.untact.repository.ResultRepository;

public interface ResultService extends BaseService<Result, ResultRequest, ResultResponse, ResultRepository> {

    // 가게 승인/거절 api
    public AcceptationResponse decideAcceptation(Long id, AcceptationRequest result);
}
