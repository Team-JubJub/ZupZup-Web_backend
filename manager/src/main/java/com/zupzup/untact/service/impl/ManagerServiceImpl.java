package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.enter.EnterException;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.model.response.EnterApprovalRes;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.repository.ManagerRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.ManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zupzup.untact.exception.enter.EnterExceptionType.NO_MATCH_ENTER;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager, ManagerReq, ManagerRes, ManagerRepository> implements ManagerService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ManagerRepository managerRepository;
    private final EnterRepository enterRepository;

    public ManagerServiceImpl(ManagerRepository repository, ManagerRepository managerRepository, EnterRepository enterRepository) {
        super(repository);
        this.managerRepository = managerRepository;
        this.enterRepository = enterRepository;
    }

    /**
     * 매니저 아이디 생성 및 저장
     */
    @Override
    public ManagerRes save(ManagerReq rq) {

        Manager m = new Manager();

        // 비밀번호 암호화 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerRes.class);
    }

    /**
     * 매니저 관련 내용 수정
     */
    @Override
    public ManagerRes update(Long id, ManagerReq rq) {

        Manager m = managerRepository.findById(rq.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 사용자는 존재하지 않습니다."));

        // 업데이트 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerRes.class);
    }

    @Override
    public EnterApprovalRes managerApproval(Long id, Boolean approval) {

        EnterApprovalRes apRes = new EnterApprovalRes();

        try {
            Enter e = enterRepository.findById(id)
                    .orElseThrow(() -> new EnterException(NO_MATCH_ENTER));

            if (approval) {

                apRes.setId(id);
                apRes.setApproval("승인 되었습니다.");
                apRes.setStoreName(e.getStoreName());

                e.setIsAccepted(true);

            } else {

                apRes.setId(id);
                apRes.setApproval("승인이 거절 되었습니다.");
                apRes.setStoreName(e.getStoreName());

                e.setIsAccepted(false);
            }

            return apRes;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return apRes;
    }
}