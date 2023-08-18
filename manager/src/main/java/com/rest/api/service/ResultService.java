package com.rest.api.service;

import com.rest.api.model.Result;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.ResultResponse;
import com.rest.api.repository.ResultRepository;
import com.rest.api.service.base.BaseService;

public interface ResultService extends BaseService<Result, ResultRequest, ResultResponse, ResultRepository> {
}
