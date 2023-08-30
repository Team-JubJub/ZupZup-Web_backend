package com.zupzup.untact.service;

import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.request.EnterRequest;
import com.zupzup.untact.model.response.EnterResponse;
import com.zupzup.untact.repository.EnterRepository;
import org.springframework.stereotype.Service;

@Service
public interface EnterService extends BaseService<Enter, EnterRequest, EnterResponse, EnterRepository> {
}
