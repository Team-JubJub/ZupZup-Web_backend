package com.rest.api.service.impl;

import com.rest.api.model.Result;
import com.rest.api.model.dto.request.AcceptationRequest;
import com.rest.api.model.dto.request.ResultRequest;
import com.rest.api.model.dto.response.AcceptationResponse;
import com.rest.api.model.dto.response.ResultResponse;
import com.rest.api.repository.ResultRepository;
import com.rest.api.service.ResultService;
import com.rest.api.service.base.BaseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl extends BaseServiceImpl<Result, ResultRequest, ResultResponse, ResultRepository> implements ResultService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ResultRepository resultRepository;
    
    public ResultServiceImpl(ResultRepository repository) {
        super(repository);
    }

    /**
     * 승인/거절 여부 결정하기
     */
    public AcceptationResponse decideAcceptation(Long id, AcceptationRequest result) {

        // 신청한 가게 정보 가져오기
        Result storeRes = resultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청입니다."));

        // 가게 승인/거절 여부 저장
        storeRes.setResult(result.getResult());
        resultRepository.save(storeRes);

        // response set
        AcceptationResponse res = new AcceptationResponse();
        if (result.getResult()) {

            res.setResultId(id);
            res.setResult("승인되었습니다.");
        } else {

            res.setResultId(id);
            res.setResult("거절되었습니다.");
        }

        return res;
    }
}
