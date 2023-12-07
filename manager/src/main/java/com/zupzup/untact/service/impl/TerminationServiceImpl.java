package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.seller.Seller;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.domain.store.Store;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.exception.store.StoreException;
import com.zupzup.untact.model.dto.response.DeleteStoreListRes;
import com.zupzup.untact.model.dto.response.StoreRes;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.repository.StoreRepository;
import com.zupzup.untact.service.TerminationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.zupzup.untact.exception.store.StoreExceptionType.NO_MATCH_STORE;

@Service
@AllArgsConstructor
public class TerminationServiceImpl implements TerminationService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 회원 탈퇴 요청 리스트
     */
    @Override
    public List<DeleteStoreListRes> gatherDeletionRequests() {

        // enterState 가 delete 인 가게 리스트
        List<Store> sList = storeRepository.findByEnterState(EnterState.DELETE);

        if (sList.size() == 0) {

            return new ArrayList<>();
        }

        List<DeleteStoreListRes> resList = new ArrayList<>();

        for (Store s : sList) {

            resList.add(modelMapper.map(s, DeleteStoreListRes.class));
        }

        return resList;
    }

    /**
     * 탈퇴 요청 상세
     */
    @Override
    public StoreRes deletionDetail(Long id) {

        Store s = storeRepository.findById(id)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));

        return modelMapper.map(s, StoreRes.class);
    }

    /**
     * 탈퇴 -> 노출 승인
     */
    @Override
    public String deleteToConfirm(Long id) {

        Store s = storeRepository.findById(id)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));

        // 노출 대기 상태로 변경
        s.setEnterState(EnterState.WAIT);
        // 노출 대기된 시간 업데이트
        s.setWaitStatusTimestamp(timeSetter());

        // 회원 탈퇴 요청 철회
        Long sellerId = s.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                        .orElseThrow(()-> new MemberException(NOT_FOUND_MEMBER));
        seller.setWantDeletion(false);

        storeRepository.save(s);
        sellerRepository.save(seller);

        return "Enter state is changed into CONFIRM";
    }

    /**
     * 탈퇴 승인
     */
    @Override
    public String confirmDelete(Long id) {

        storeRepository.deleteById(id);

        return "delete completed";
    }

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
