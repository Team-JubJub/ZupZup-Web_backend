package com.zupzup.untact.api;

import com.zupzup.untact.model.dto.response.StoreRes;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TerminationController {

    ResponseEntity<List<StoreRes>> gatherDeletionRequests(); // 회원탈퇴 요청 가게 리스트
    ResponseEntity<StoreRes> deletionDetail(Long id); // 탈퇴 요청 상세
    ResponseEntity<String> deleteToConfirm(Long id); // 탈퇴 요청 -> 노출 승인
    ResponseEntity<String> confirmDelete(Long id); // 탈퇴 승인
}
