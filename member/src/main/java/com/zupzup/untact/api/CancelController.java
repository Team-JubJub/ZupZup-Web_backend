package com.zupzup.untact.api;

import com.zupzup.untact.exception.apple.AppleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CancelController {

    ResponseEntity<String> wantDelete(Long id) throws AppleException; // 회원 탈퇴 요청
}
