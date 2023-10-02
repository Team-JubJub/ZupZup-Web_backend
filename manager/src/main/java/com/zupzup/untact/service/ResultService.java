package com.zupzup.untact.service;

import com.zupzup.untact.model.dto.request.EnterUpdateReq;
import com.zupzup.untact.model.dto.request.StateReq;
import com.zupzup.untact.model.dto.request.StoreUpdateReq;
import com.zupzup.untact.model.dto.response.*;

import java.util.List;

public interface ResultService {

    List<EnterListRes> enterList(); // 매장 전체보기 (신규 신청)
    EnterRes enterDetail(Long id); // 매장 상세
    String newToWait(StateReq rq); // 매장 노출 대기로 변경 = store 엔티티에 저장
    EnterRes updateEnterDetail(Long id, EnterUpdateReq rq); // 매장 관련 내용 수정
    List<EnterListRes> searchEnterList(String keyword); // 매장 검색

    List<WaitStoreListRes> waitStoreList(); // 메뉴 전체보기 (노출 대기)
    StoreRes storeDetail(Long id); // 메뉴 단건 보기
    String deleteStore(Long id); // 매장 삭제
    String waitToConfirm(StateReq rq);// 매장 입점 확정으로 수정
    StoreRes updateStoreDetail(Long id, StoreUpdateReq rq); // 매장 관련 내용 수정
    List<WaitStoreListRes> searchWaitStoreList(String keyword); // 매장 검색

    List<ConfirmStoreListRes> confirmStoreList(); // 입점 매장 목록 - 제네릭 사용 예정
    StoreRes confirmStoreDetail(Long id); // 입점 매장 상세
    String confirmToWait(StateReq rq); // 입점 매장 노출 대기로 변경
    List<ConfirmStoreListRes> searchConfirmStoreList(String keyword); // 매장 검색
}
