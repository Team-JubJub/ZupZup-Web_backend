package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.seller.Seller;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.domain.store.Store;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.repository.StoreRepository;
import com.zupzup.untact.service.CancelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zupzup.untact.exception.member.MemberExceptionType.APPLE_CANNOT_FIND_MEMBER;

@Service
@AllArgsConstructor
public class CancelServiceImpl implements CancelService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

    /**
     * 회원탈퇴 요청
     */
    @Override
    public String wantDelete(Long id) {

        // 회원 찾기
        Seller s = sellerRepository.findById(id)
                .orElseThrow(() -> new MemberException(APPLE_CANNOT_FIND_MEMBER));
        // 가게 찾기
        Store store = storeRepository.findBySellerId(id);

        // 삭제 요청 상태 변경
        s.setWantDeletion(true);
        store.setEnterState(EnterState.DELETE);

        sellerRepository.save(s);
        storeRepository.save(store);

        String res = "ID: " + id + " is deleted";

        return res;
    }
}
