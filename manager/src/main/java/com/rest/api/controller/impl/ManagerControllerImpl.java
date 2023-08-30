package com.rest.api.controller.impl;

import com.rest.api.controller.ManagerController;
import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.repository.ManagerRepository;
import com.rest.api.service.ManagerService;
import com.zupzup.untact.controller.BaseControllerImpl;
import com.zupzup.untact.service.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/manager")
public class ManagerControllerImpl extends BaseControllerImpl<Manager, ManagerRequest, ManagerResponse, ManagerRepository> implements ManagerController{

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ManagerService managerService;

    public ManagerControllerImpl(BaseService<Manager, ManagerRequest, ManagerResponse, ManagerRepository> baseService) {
        super(baseService);
    }
}
