package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.auth.seller.Seller;
import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.domain.store.Store;
import com.zupzup.untact.exception.apple.AppleException;
import com.zupzup.untact.repository.SellerRepository;
import com.zupzup.untact.repository.StoreRepository;
import com.zupzup.untact.service.CancelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zupzup.untact.exception.apple.AppleExceptionType.NO_MEMBER;

@Service
@AllArgsConstructor
public class CancelServiceImpl implements CancelService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;
    private final String errMsg = "Cannot find member";

    /**
     * 회원탈퇴 요청
     */
    @Override
    public String wantDelete(Long id) throws AppleException {

        // 회원 찾기
        Seller s = sellerRepository.findById(id)
                .orElseThrow(() -> new AppleException(NO_MEMBER));
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
