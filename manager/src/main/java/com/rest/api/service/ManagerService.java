package com.rest.api.service;

import com.rest.api.model.Manager;
import com.rest.api.model.dto.request.ManagerRequest;
import com.rest.api.model.dto.response.ManagerResponse;
import com.rest.api.repository.ManagerRepository;
import com.zupzup.untact.service.BaseService;

public interface ManagerService extends BaseService<Manager, ManagerRequest, ManagerResponse, ManagerRepository> {
}
