package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Member;
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

            Enter e = new Enter();
            e.saveEnter(rq, m);

            enterRepository.save(e);

            return modelMapper.map(e, EnterRes.class);
        } catch (Exception e) {

            EnterRes rs = new EnterRes();
            rs.setId(rq.getId());
            rs.setStoreName("id 값에 해당하는 사용자는 존재하지 않습니다.");

            return rs;
        }
    }
}
