package com.rest.api.service.impl;

import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.repository.ManagerRepository;
import com.rest.api.service.ManagerService;
import com.rest.api.service.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager, ManagerRequest, ManagerResponse, ManagerRepository> implements ManagerService {

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
    public ManagerResponse save(ManagerRequest rq) throws Exception {

        Manager m = new Manager();

        // 비밀번호 암호화 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerResponse.class);
    }

    /**
     * 매니저 관련 내용 수정
     */
    @Override
    public ManagerResponse update(ManagerRequest rq) throws Exception {

        Manager m = managerRepository.findById(rq.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 사용자는 존재하지 않습니다."));

        // 업데이트 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerResponse.class);
    }
}
