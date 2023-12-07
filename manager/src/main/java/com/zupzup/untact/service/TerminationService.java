package com.zupzup.untact.service;

import com.zupzup.untact.model.dto.response.StoreRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TerminationService {

    List<StoreRes> gatherDeletionRequests(); // 회원 탈퇴 요청 리스트
    StoreRes deletionDetail(Long id); // 탈퇴 요청 상세
    String deleteToConfirm(Long id); // 탈퇴 요청 -> 노출 승인
    String confirmDelete(Long id); // 탈퇴 승인
}
