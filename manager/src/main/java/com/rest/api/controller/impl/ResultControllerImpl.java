package com.rest.api.controller.impl;

import com.rest.api.controller.ResultController;
import com.rest.api.controller.base.BaseControllerImpl;
import com.rest.api.model.Result;
import com.rest.api.model.dto.request.AcceptationRequest;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.AcceptationResponse;
import com.rest.api.model.dto.response.ResultResponse;
import com.rest.api.repository.ResultRepository;
import com.rest.api.service.ManagerService;
import com.rest.api.service.ResultService;
import com.rest.api.service.base.BaseService;
import com.rest.api.service.impl.ResultServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(path = "/result")
public class ResultControllerImpl extends BaseControllerImpl<Result, ResultRequest, ResultResponse, ResultRepository> implements ResultController {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ResultServiceImpl resultService;

    public ResultControllerImpl(BaseService<Result, ResultRequest, ResultResponse, ResultRepository> baseService) {
        super(baseService);
    }

    /**
     * 가게 승인/거절 api
     * id : result id
     */
    @PostMapping("/{id}")
    public ResponseEntity decideAcceptation(@PathVariable Long id, @RequestBody AcceptationRequest result) {

        AcceptationResponse res = resultService.decideAcceptation(id, result);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
