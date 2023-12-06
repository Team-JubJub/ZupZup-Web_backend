package com.zupzup.untact.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CancelController {

    ResponseEntity<String> wantDelete(Long id); // 회원 탈퇴 요청
}
