package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.Result;
import com.zupzup.untact.model.dto.request.AcceptationRequest;
import com.zupzup.untact.model.dto.request.ResultRequest;
import com.zupzup.untact.model.dto.response.AcceptationResponse;
import com.zupzup.untact.model.dto.response.ResultResponse;
import com.zupzup.untact.repository.ResultRepository;
import com.zupzup.untact.service.ResultService;
import com.zupzup.untact.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultServiceImpl extends BaseServiceImpl<Result, ResultRequest, ResultResponse, ResultRepository> implements ResultService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ResultRepository resultRepository;
    
    public ResultServiceImpl(ResultRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public ResultResponse update(Long id, ResultRequest rq) throws Exception {

        Result r = resultRepository.findById(id)
                        .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        r.updateResult(rq);

        resultRepository.save(r);

        return modelMapper.map(r, ResultResponse.class);
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
