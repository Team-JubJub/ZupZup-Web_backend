package com.rest.api.controller.impl;

import com.rest.api.model.dto.request.LoginRequest;
import com.rest.api.service.impl.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginReq) {
        return new ResponseEntity(loginService.login(loginReq), HttpStatus.OK);
    }
}
