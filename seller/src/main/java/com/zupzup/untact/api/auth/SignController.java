package com.zupzup.untact.api.auth;

import com.zupzup.untact.model.request.MemberFindReq;
import com.zupzup.untact.model.request.MemberLoginReq;
import com.zupzup.untact.model.request.MemberPwdReq;
import com.zupzup.untact.model.response.MemberLoginRes;
import com.zupzup.untact.model.response.MemberRes;
import com.zupzup.untact.service.auth.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/login")
    public ResponseEntity<MemberLoginRes> signIn(@RequestBody MemberLoginReq rq) throws Exception {

        return new ResponseEntity<>(signService.login(rq), HttpStatus.OK);
    }
    /**
     * 비밀번호 변경
     */
    @PostMapping("/change/{id}")
    public ResponseEntity changePwd(@PathVariable Long id, @RequestBody MemberPwdReq rq) {

        String rs = signService.changePwd(id, rq);

        return new ResponseEntity(rs, HttpStatus.OK);
    }

    /**
     * 아이디 찾기
     */
    @PostMapping("/find")
    public ResponseEntity<MemberRes> findLoginId(@RequestBody MemberFindReq rq) {

        MemberRes rs = signService.findLoginId(rq);
        return new ResponseEntity(rs, HttpStatus.OK);
    }

}
