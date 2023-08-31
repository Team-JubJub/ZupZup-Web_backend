package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.ManagerController;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.repository.ManagerRepository;
import com.zupzup.untact.service.ManagerService;
import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.service.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/manager")
public class ManagerControllerImpl extends BaseControllerImpl<Manager, ManagerReq, ManagerRes, ManagerRepository> implements ManagerController{

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ManagerService managerService;

    public ManagerControllerImpl(BaseService<Manager, ManagerReq, ManagerRes, ManagerRepository> baseService) {
        super(baseService);
    }
}
