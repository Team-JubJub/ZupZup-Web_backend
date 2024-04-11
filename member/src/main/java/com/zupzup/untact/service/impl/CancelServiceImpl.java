package com.zupzup.untact.service.impl;

import com.zupzup.untact.model.domain.auth.seller.Seller;
import com.zupzup.untact.model.domain.enums.EnterState;
import com.zupzup.untact.model.domain.store.Store;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.exception.store.StoreException;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.repository.StoreRepository;
import com.zupzup.untact.service.CancelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.zupzup.untact.exception.member.MemberExceptionType.ALREADY_WANTED_DELETE;
import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.zupzup.untact.exception.store.StoreExceptionType.NO_MATCH_STORE;

@Service
@AllArgsConstructor
public class CancelServiceImpl implements CancelService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

    /**
     * 회원탈퇴 요청
     */
    @Override
    @Transactional
    public String wantDelete(Long storeId) {

        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));
        // 회원 찾기
        Seller s = sellerRepository.findById(store.getSellerId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        if (s.getWantDeletion()) {
            throw new MemberException(ALREADY_WANTED_DELETE);
        }

        // 삭제 요청 상태 변경
        s.setWantDeletion(true);
        store.setEnterState(EnterState.DELETE);
        store.setDeleteStatusTimestamp(timeSetter());

        sellerRepository.save(s);
        storeRepository.save(store);

        String res = "ID: " + storeId + " is deleted";

        return res;
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
