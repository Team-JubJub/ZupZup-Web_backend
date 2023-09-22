package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.enums.EnterState;
import com.zupzup.untact.model.request.EnterReq;
import com.zupzup.untact.model.response.EnterRes;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.EnterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterServiceImpl extends BaseServiceImpl<Enter, EnterReq, EnterRes, EnterRepository> implements EnterService {

    @Autowired
    ModelMapper modelMapper;

    private final MemberRepository memberRepository;
    private final EnterRepository enterRepository;

    public EnterServiceImpl(EnterRepository repository, MemberRepository memberRepository, EnterRepository enterRepository) {
        super(repository);
        this.memberRepository = memberRepository;
        this.enterRepository = enterRepository;
    }

    /**
     * 가게 내용 저장
     */
    @Override
    public EnterRes save(EnterReq rq) {

        try {

            Member m = memberRepository.findById(rq.getId()).get();

            // 사장님과 입점 신청은 1:N 관계
            // 입점 신청에서 받은 내용들 저장 + 문의 상태는 NEW 로 설정 (관리자용)
            Enter e = Enter.builder()
                    .name(m.getName())
                    .phoneNum(m.getPhoneNum())
                    .storeName(rq.getStoreName())
                    .storeAddress(rq.getStoreAddress())
                    .crNumber(rq.getCrNumber())
                    .state(EnterState.NEW)
                    .build();

            enterRepository.save(e);

            return modelMapper.map(e, EnterRes.class);
        } catch (Exception e) {

            // 사용자가 존재하지 않을 경우 에러 발생
            EnterRes rs = new EnterRes();
            rs.setId(rq.getId());
            rs.setStoreName("id 값에 해당하는 사용자는 존재하지 않습니다.");

            return rs;
        }
    }
}
