package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.ResultController;
import com.zupzup.untact.model.Result;
import com.zupzup.untact.model.dto.request.AcceptationRequest;
import com.zupzup.untact.model.dto.request.ResultRequest;
import com.zupzup.untact.model.dto.response.AcceptationResponse;
import com.zupzup.untact.model.dto.response.ResultResponse;
import com.zupzup.untact.repository.ResultRepository;
import com.zupzup.untact.service.impl.ResultServiceImpl;
import com.zupzup.untact.api.BaseControllerImpl;
import com.zupzup.untact.service.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
