package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.request.EnterRequest;
import com.zupzup.untact.model.response.EnterResponse;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.EnterService;
import org.springframework.stereotype.Service;

@Service
public class EnterServiceImpl extends BaseServiceImpl<Enter, EnterRequest, EnterResponse, EnterRepository> implements EnterService {

    public EnterServiceImpl(EnterRepository repository) {
        super(repository);
    }
}
