package com.zupzup.untact.api.impl;

import com.zupzup.untact.api.ResultController;
import com.zupzup.untact.model.dto.request.EnterUpdateReq;
import com.zupzup.untact.model.dto.request.StateReq;
import com.zupzup.untact.model.dto.request.StoreUpdateReq;
import com.zupzup.untact.model.dto.response.*;
import com.zupzup.untact.service.impl.ResultServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResultControllerImpl implements ResultController {

    private final ResultServiceImpl resultService;

    // --------- NEW ---------

    /**
     * 신규 매장 전체 리스트
     */
    @Override
    @GetMapping("/new")
    public List<EnterListRes> enterList() {
        return resultService.enterList();
    }

    /**
     * 신규 신청 매장 상세
     */
    @Override
    @GetMapping("/new/{id}")
    public EnterRes enterDetail(@PathVariable Long id) {
        return resultService.enterDetail(id);
    }

    /**
     * 신규 신청에서 노출 대기로 변경
     * (store 엔티티로 저장)
     */
    @Override
    @PostMapping("/new")
    public String newToWait(@RequestBody StateReq rq) {
        return resultService.newToWait(rq);
    }

    /**
     * 신청서 수정
     */
    @Override
    @PostMapping("/new/{id}")
    public EnterRes updateEnterDetail(@PathVariable Long id, @RequestBody EnterUpdateReq rq) {
        return resultService.updateEnterDetail(id, rq);
    }

    /**
     * 매장 검색
     */
    @Override
    @GetMapping("/new/search")
    public List<EnterListRes> searchEnterList(@RequestParam String keyword) {
        return resultService.searchEnterList(keyword);
    }

    // --------- WAIT ---------

    /**
     * 노출 대기 가게 전체 보기
     */
    @Override
    @GetMapping("/wait")
    public List<WaitStoreListRes> waitStoreList() {
        return resultService.waitStoreList();
    }

    /**
     * 노출 대기 상태 가게 상세 보기
     */
    @Override
    @GetMapping("/wait/{id}")
    public StoreRes storeDetail(@PathVariable Long id) {
        return resultService.storeDetail(id);
    }

    /**
     * 가게 삭제
     */
    @Override
    @DeleteMapping("/wait/{id}")
    public String deleteStore(@PathVariable Long id) {
        return resultService.deleteStore(id);
    }

    /**
     * 노출 대기 상태에서 노출 승인 상태로 변경
     */
    @Override
    @PostMapping("/wait")
    public String waitToConfirm(@RequestBody StateReq rq) {
        return resultService.waitToConfirm(rq);
    }

    /**
     * 가게 내용 수정
     */
    @Override
    @PostMapping("/wait/{id}")
    public StoreRes updateStoreDetail(@PathVariable Long id, @RequestBody StoreUpdateReq rq) {
        return resultService.updateStoreDetail(id, rq);
    }

    /**
     * 매장 검색
     */
    @Override
    @GetMapping("/wait/search")
    public List<WaitStoreListRes> searchWaitStoreList(@RequestParam String keyword) {
        return resultService.searchWaitStoreList(keyword);
    }

    // --------- CONFIRM ---------

    /**
     * 노출 승인 가게 전체 보기
     */
    @Override
    @GetMapping("/confirm")
    public List<ConfirmStoreListRes> confirmStoreList() {
        return resultService.confirmStoreList();
    }

    /**
     * 노출 승인 된 가게 상세 보기
     */
    @Override
    @GetMapping("/confirm/{id}")
    public StoreRes confirmStoreDetail(@PathVariable Long id) {
        return resultService.confirmStoreDetail(id);
    }

    /**
     * 노출 승인에서 노출 대기로 변경
     */
    @Override
    @PostMapping("/confirm")
    public String confirmToWait(@RequestBody StateReq rq) {
        return resultService.confirmToWait(rq);
    }

    /**
     * 매장 검색
     */
    @Override
    @GetMapping("/confirm/search")
    public List<ConfirmStoreListRes> searchConfirmStoreList(@RequestParam String keyword) {
        return resultService.searchConfirmStoreList(keyword);
    }
}
