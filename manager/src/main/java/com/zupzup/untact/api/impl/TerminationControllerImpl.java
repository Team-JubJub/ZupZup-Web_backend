package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.TerminationController;
import com.zupzup.untact.model.dto.response.StoreRes;
import com.zupzup.untact.service.impl.TerminationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delete")
public class TerminationControllerImpl implements TerminationController {

    private final TerminationServiceImpl terminationService;

    /**
     * 회원 탈퇴 요청 리스트
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<StoreRes>> gatherDeletionRequests() {

        List<StoreRes> resList = terminationService.gatherDeletionRequests();
        return new ResponseEntity<>(resList, HttpStatus.OK);
    }

    /**
     * 탈퇴 요청 상세
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<StoreRes> deletionDetail(@PathVariable Long id) {

        StoreRes res = terminationService.deletionDetail(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 탈퇴 요청 -> 노출 승인
     */
    @PatchMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteToConfirm(@PathVariable Long id) {

        String res = terminationService.deleteToConfirm(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 탈퇴 승인
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> confirmDelete(@PathVariable Long id) {

        String res = terminationService.confirmDelete(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
