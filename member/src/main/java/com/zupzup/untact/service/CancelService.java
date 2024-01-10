package com.zupzup.untact.service;

import org.springframework.stereotype.Service;

@Service
public interface CancelService {

    String wantDelete(Long id); // 회원탈퇴 요청
}
