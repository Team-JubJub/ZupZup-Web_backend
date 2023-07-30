package com.rest.api.controller.impl;

import com.rest.api.controller.ManagerController;
import com.rest.api.controller.base.BaseControllerImpl;
import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.model.dto.response.ResultResponse;
import com.rest.api.repository.ManagerRepository;
import com.rest.api.service.ManagerService;
import com.rest.api.service.base.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/result")
    public ResponseEntity decideResult(@RequestBody ResultRequest rRs) {

        ResultResponse response = new ResultResponse();
        if (rRs.getResult()) {
            modelMapper.map(rRs, ResultResponse.class);
            response.setIsAccepted("승인되었습니다.");
        } else {
            response.setIsAccepted("거절되었습니다.");
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
