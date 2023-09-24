package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.dto.request.EnterUpdateReq;
import com.zupzup.untact.model.dto.request.StateReq;
import com.zupzup.untact.model.dto.request.StoreUpdateReq;
import com.zupzup.untact.model.dto.response.EnterRes;
import com.zupzup.untact.model.dto.response.StoreRes;
import com.zupzup.untact.service.ResultService;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    // --------- NEW ---------
    /**
     * 신규 신청 매장 상세
     */
    @Override
    public EnterRes enterDetail(Long id) {
        return null;
    }

    /**
     * 신규 신청에서 노출 대기로 변경
     * (store 엔티티로 저장)
     */
    @Override
    public String newToWait(StateReq rq) {
        return null;
    }

    /**
     * 신청서 수정
     */
    @Override
    public EnterRes updateEnterDetail(Long id, EnterUpdateReq rq) {
        return null;
    }

    // --------- WAIT ---------
    /**
     * 노출 대기 상태 가게 상세 보기
     */
    @Override
    public StoreRes storeDetail(Long id) {
        return null;
    }

    /**
     * 가게 삭제
     */
    @Override
    public String deleteStore(Long id) {
        return null;
    }

    /**
     * 노출 대기 상태에서 노출 승인 상태로 변경
     */
    @Override
    public String waitToConfirm(StateReq rq) {
        return null;
    }

    /**
     * 가게 내용 수정
     */
    @Override
    public StoreRes updateStoreDetail(StoreUpdateReq rq) {
        return null;
    }

    // --------- CONFIRM ---------
    /**
     * 노출 승인 된 가게 상세 보기
     */
    @Override
    public StoreRes confirmStoreDetail(Long id) {
        return null;
    }

    /**
     * 노출 승인에서 노출 대기로 변경
     */
    @Override
    public String confirmToWait(StateReq rq) {
        return null;
    }
}
