package com.zupzup.untact.service;

import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.dto.request.ManagerRequest;
import com.zupzup.untact.model.dto.response.ManagerResponse;
import com.zupzup.untact.repository.ManagerRepository;

public interface ManagerService extends BaseService<Manager, ManagerRequest, ManagerResponse, ManagerRepository> {
}
