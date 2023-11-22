package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.seller.Seller;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.domain.enums.StoreCategory;
import com.zupzup.untact.domain.store.Store;
import com.zupzup.untact.exception.ManagerException;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.dto.request.EnterUpdateReq;
import com.zupzup.untact.model.dto.request.StateReq;
import com.zupzup.untact.model.dto.request.StoreUpdateReq;
import com.zupzup.untact.model.dto.response.*;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.repository.StoreRepository;
import com.zupzup.untact.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.zupzup.untact.exception.ManagerExceptionType.EMPTY_LIST;
import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    @Autowired
    ModelMapper modelMapper;

    private final EnterRepository enterRepository;
    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

    // --------- NEW ---------

    /**
     * 신규 신청 매장 전체 보기
     */
    @Override
    public List<EnterListRes> enterList() {

        List<Enter> eList = enterRepository.findByState(EnterState.NEW);

        // list 길이가 0일 경우 에러 발생
        if (eList.size() == 0) {
            throw new ManagerException(EMPTY_LIST);
        }

        List<EnterListRes> eListRes = new ArrayList<>();

        for (Enter e : eList) {

            eListRes.add(modelMapper.map(e, EnterListRes.class));
        }

        return eListRes;
    }

    /**
     * 신규 신청 매장 상세
     */
    @Override
    public EnterRes enterDetail(Long id) {

        Enter e = enterRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        // 신규 신청 매장이 아닐 경우
        if (e.getState() != EnterState.NEW) {

            EnterRes rs = new EnterRes();
            rs.setName("신규 신청 매장이 아닙니다.");
            rs.setMemberLoginId(e.getState().toString());

            return rs;
        }

        return modelMapper.map(e, EnterRes.class);
    }

    /**
     * 신규 신청에서 노출 대기로 변경
     * (store 엔티티로 저장)
     */
    @Override
    public String newToWait(StateReq rq) {

        Enter e = enterRepository.findById(rq.getId())
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        if (!rq.getIsAccepted()) {

            // isAccepted 가 false 일 경우 원하는 로직 찾지 못함
            return "Cannot find request";
        }

        // Store 엔티티로 정보 이전 및 저장
        Store s = Store.StoreBuilder()
                .sellerName(e.getName())
                .sellerId(e.getMember().getSellerId())
                .sellerContact(e.getPhoneNum())
                .storeContact(e.getStoreNum())
                .storeName(e.getStoreName())
                .storeAddress(e.getStoreAddress())
                .longitude(e.getLongitude())
                .latitude(e.getLatitude())
                .crNumber(e.getCrNumber())
                .enterState(EnterState.WAIT)
                .waitStatusTimestamp(timeSetter())
                .build();

        e.setIsAccepted(true);
        e.setState(EnterState.WAIT);

        storeRepository.save(s);

        return "Enter state is changed into WAIT";
    }

    /**
     * 신청서 삭제
     */
    @Override
    public String deleteEnter(Long id) {

        Enter e = enterRepository.findById(id)
                        .orElseThrow(() -> new ManagerException(EMPTY_LIST));
        e.setDeleted(true);

        return "Enter is deleted";
    }

    /**
     * 신청서 수정
     */
    @Override
    public EnterRes updateEnterDetail(Long id, EnterUpdateReq rq) {

        Enter e = enterRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        // 관련 내용 수정
        e.setStoreNum(rq.getStoreNum());
        e.setStoreName(rq.getStoreName());
        e.setStoreAddress(rq.getStoreAddress());

        enterRepository.save(e);

        return modelMapper.map(e, EnterRes.class);
    }

    /**
     * 신규 신청 매장 검색
     */
    @Override
    public List<EnterListRes> searchEnterList(String keyword) {

        List<Enter> eList = enterRepository.searchByStoreNameContainingAndState(keyword, EnterState.NEW);

        // 비어 있을 경우 에러 발생
        if (eList.isEmpty()) {
            throw new ManagerException(EMPTY_LIST);
        }

        List<EnterListRes> eListRes = new ArrayList<>();

        for (Enter e : eList) {

            eListRes.add(modelMapper.map(e, EnterListRes.class));
        }

        return eListRes;
    }

    // --------- WAIT ---------

    /**
     * 노출 대기 매장 전체 보기
     */
    @Override
    public List<WaitStoreListRes> waitStoreList() {

        List<Store> sList = storeRepository.findByEnterState(EnterState.WAIT);

        // list 사이즈가 0일 경우 에러 발생
        if (sList.size() == 0) {
            throw new ManagerException(EMPTY_LIST);
        }

        List<WaitStoreListRes> wsList = new ArrayList<>();

        for (Store s : sList) {

            wsList.add(modelMapper.map(s, WaitStoreListRes.class));
        }

        return wsList;
    }

    /**
     * 노출 대기 매장 상세 보기
     */
    @Override
    public StoreRes storeDetail(Long id) {

        Store s = storeRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        // 노출 대기 매장이 아닐 경우
        if (s.getEnterState() != EnterState.WAIT) {

            StoreRes rs = new StoreRes();
            rs.setSellerName("노출 대기 매장이 아닙니다.");
            rs.setSellerLoginId(s.getEnterState().toString());

            return rs;
        }

        // 판매자 로그인 아이디 가져오기
        Seller seller = sellerRepository.findById(s.getSellerId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        StoreRes rs = modelMapper.map(s, StoreRes.class);
        rs.setSellerLoginId(seller.getLoginId());

        return rs;
    }

    /**
     * 매장 삭제
     */
    @Override
    public String deleteStore(Long id) {

        // Store 존재 여부 확인
        storeRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        storeRepository.deleteById(id);

        return "Delete completed";
    }

    /**
     * 노출 대기 상태에서 노출 승인 상태로 변경
     */
    @Override
    public String waitToConfirm(StateReq rq) {

        Store s = storeRepository.findById(rq.getId())
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        if (!rq.getIsAccepted()) {

            // isAccepted 가 false 일 경우 원하는 로직 찾지 못함
            return "Cannot find request";
        }

        s.setEnterState(EnterState.CONFIRM);
        s.setConfirmStatusTimestamp(timeSetter());

        storeRepository.save(s);

        return "Enter state is changed into CONFIRM";
    }

    /**
     * 매장 내용 수정
     */
    @Override
    public StoreRes updateStoreDetail(Long id, StoreUpdateReq rq) {

        Store s = storeRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        // 카테고리 enum 으로 바꾸는 작업
        StoreCategory sc = StoreCategory.valueOf(rq.getCategory().toUpperCase());

        s.setStoreContact(rq.getStoreContact());
        s.setStoreName(rq.getStoreName());
        s.setStoreAddress(rq.getStoreAddress());
        s.setCategory(sc);

        storeRepository.save(s);

        return modelMapper.map(s, StoreRes.class);
    }

    /**
     * 노츨 대기 매장 검색
     */
    @Override
    public List<WaitStoreListRes> searchWaitStoreList(String keyword) {

        List<Store> sList = storeRepository.searchByStoreNameContainingAndEnterState(keyword, EnterState.WAIT);

        // 리스트가 비어있을 경우
        if (sList.isEmpty()) {

            throw new ManagerException(EMPTY_LIST);
        }

        List<WaitStoreListRes> wsList = new ArrayList<>();

        for (Store s : sList) {

            wsList.add(modelMapper.map(s, WaitStoreListRes.class));
        }

        return wsList;
    }

    // --------- CONFIRM ---------

    /**
     * 노출 승인 매장 전체 보기
     */
    @Override
    public List<ConfirmStoreListRes> confirmStoreList() {

        List<Store> sList = storeRepository.findByEnterState(EnterState.CONFIRM);

        // list 사이즈가 0일 경우 에러 발생
        if (sList.size() == 0) {
            throw new ManagerException(EMPTY_LIST);
        }

        List<ConfirmStoreListRes> csList = new ArrayList<>();

        for (Store s : sList) {

            csList.add(modelMapper.map(s, ConfirmStoreListRes.class));
        }

        return csList;
    }

    /**
     * 노출 승인 매장 상세 보기
     */
    @Override
    public StoreRes confirmStoreDetail(Long id) {

        Store s = storeRepository.findById(id)
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        // 입점된 매장이 아닐 경우
        if (s.getEnterState() != EnterState.CONFIRM) {

            StoreRes rs = new StoreRes();
            rs.setSellerName("입점된 매장이 아닙니다.");
            rs.setSellerLoginId(s.getEnterState().toString());

            return rs;
        }

        // 판매자 로그인 아이디 가져오기
        Seller seller = sellerRepository.findById(s.getSellerId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        StoreRes rs = modelMapper.map(s, StoreRes.class);
        rs.setSellerLoginId(seller.getLoginId());

        return rs;

    }

    /**
     * 노출 승인에서 노출 대기로 변경
     */
    @Override
    public String confirmToWait(StateReq rq) {

        Store s = storeRepository.findById(rq.getId())
                .orElseThrow(() -> new ManagerException(EMPTY_LIST));

        if (!rq.getIsAccepted()) {

            // isAccepted 가 false 일 경우 원하는 로직 찾지 못함
            return "Cannot find request";
        }

        s.setEnterState(EnterState.WAIT);
        s.setWaitStatusTimestamp(timeSetter());

        storeRepository.save(s);

        return "Enter state is changed into WAIT";
    }

    /**
     * 노출 승인 매장 검색
     */
    @Override
    public List<ConfirmStoreListRes> searchConfirmStoreList(String keyword) {

        List<Store> sList = storeRepository.searchByStoreNameContainingAndEnterState(keyword, EnterState.CONFIRM);

        // 리스트가 비어있을 경우
        if (sList.isEmpty()) {

            throw new ManagerException(EMPTY_LIST);
        }

        List<ConfirmStoreListRes> csList = new ArrayList<>();

        for (Store s : sList) {

            csList.add(modelMapper.map(s, ConfirmStoreListRes.class));
        }

        return csList;
    }

    //-----------------------------
    /**
     * 시간 포매팅
     */
    private String timeSetter() {

        ZonedDateTime nowTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedOrderTime = nowTime.format(formatter);

        return formattedOrderTime;
    }
}
