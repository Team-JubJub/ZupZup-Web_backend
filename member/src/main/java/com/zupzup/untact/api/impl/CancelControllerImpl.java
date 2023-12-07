package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.CancelController;
import com.zupzup.untact.exception.apple.AppleException;
import com.zupzup.untact.service.impl.CancelServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/cancel")
public class CancelControllerImpl implements CancelController {

    private final CancelServiceImpl cancelService;

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<String> wantDelete(@PathVariable Long id) throws AppleException {

        String res = cancelService.wantDelete(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
