package com.zupzup.untact.exception.apple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppleExceptionAdvice {

    @ExceptionHandler(AppleException.class) // AppleException 처리를 위한 핸들러 명시
    public ResponseEntity<String> handleAppleException(AppleException ex) {
        log.error("AppleException errorMessage(): {}", ex.getExceptionType().getErrMsg());

        // ResponseEntity의 본문으로 직접 문자열 전달
        return new ResponseEntity<>(ex.getExceptionType().getErrMsg(), HttpStatus.BAD_REQUEST);
    }
}
