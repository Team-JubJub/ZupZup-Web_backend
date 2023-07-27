package com.rest.api.service.impl;

import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.repository.ManagerRepository;
import com.rest.api.service.ManagerService;
import com.rest.api.service.base.BaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends BaseServiceImpl<Manager, ManagerRequest, ManagerResponse, ManagerRepository> implements ManagerService {

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ModelMapper modelMapper;

    public MemberServiceImpl(ManagerRepository repository) {
        super(repository);
    }

    @Override
    public ManagerResponse update(ManagerRequest rq) throws Exception {

        // 동명이인 불가
        Manager m = managerRepository.findByName(rq.getName());
        m.updateManager(rq);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerResponse.class);
    }
}
