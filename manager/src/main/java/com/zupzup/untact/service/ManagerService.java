package com.zupzup.untact.service;

import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.repository.ManagerRepository;

public interface ManagerService extends BaseService<Manager, ManagerReq, ManagerRes, ManagerRepository> {
}
