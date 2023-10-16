package com.zupzup.untact.api.auth;

import com.zupzup.untact.model.dto.request.ManagerLoginReq;
import com.zupzup.untact.model.dto.response.ManagerLoginRes;
import com.zupzup.untact.service.auth.ManagerSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerSignController {

    private final ManagerSignService managerSignService;

    @PostMapping("/manager/login")
    public ResponseEntity<ManagerLoginRes> signIn(@RequestBody ManagerLoginReq rq) throws Exception {

        ManagerLoginRes rs = managerSignService.login(rq);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

}

