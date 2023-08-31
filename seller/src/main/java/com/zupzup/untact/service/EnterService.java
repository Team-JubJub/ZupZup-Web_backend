package com.zupzup.untact.service;

import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.request.EnterReq;
import com.zupzup.untact.model.response.EnterRes;
import com.zupzup.untact.repository.EnterRepository;
import org.springframework.stereotype.Service;

@Service
public interface EnterService extends BaseService<Enter, EnterReq, EnterRes, EnterRepository> {
}
