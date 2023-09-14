package com.zupzup.untact.api.auth;

import com.zupzup.untact.model.dto.request.ManagerLoginReq;
import com.zupzup.untact.model.dto.response.ManagerLoginRes;
import com.zupzup.untact.service.auth.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/login")
    public ResponseEntity<ManagerLoginRes> signIn(@RequestBody ManagerLoginReq rq) throws Exception {

        return new ResponseEntity<>(signService.login(rq), HttpStatus.OK);
    }

}

