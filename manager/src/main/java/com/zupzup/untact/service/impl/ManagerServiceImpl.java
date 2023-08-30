package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.repository.ManagerRepository;
import com.zupzup.untact.service.ManagerService;
import com.zupzup.untact.service.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager, ManagerReq, ManagerRes, ManagerRepository> implements ManagerService {

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ManagerServiceImpl(ManagerRepository repository) {
        super(repository);
    }

    /**
     * 매니저 아이디 생성 및 저장
     */
    @Override
    public ManagerRes save(ManagerReq rq) throws Exception {

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
    public ManagerRes update(Long id, ManagerReq rq) throws Exception {

        Manager m = managerRepository.findById(rq.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 사용자는 존재하지 않습니다."));

        // 업데이트 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerRes.class);
    }
}
