package com.zupzup.untact.service;

import com.zupzup.untact.exception.apple.AppleException;
import org.springframework.stereotype.Service;

@Service
public interface CancelService {

    String wantDelete(Long id) throws AppleException; // 회원탈퇴 요청
}
