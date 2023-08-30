package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.api.EnterController;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.request.EnterRequest;
import com.zupzup.untact.model.response.EnterResponse;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.service.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enter")
public class EnterControllerImpl extends BaseControllerImpl<Enter, EnterRequest, EnterResponse, EnterRepository> implements EnterController {

    public EnterControllerImpl(BaseService<Enter, EnterRequest, EnterResponse, EnterRepository> baseService) {
        super(baseService);
    }
}
