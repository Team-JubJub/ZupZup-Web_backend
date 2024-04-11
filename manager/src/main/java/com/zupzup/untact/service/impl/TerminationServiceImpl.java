package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.exception.store.StoreException;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.domain.auth.seller.Seller;
import com.zupzup.untact.model.domain.auth.user.User;
import com.zupzup.untact.model.domain.enums.EnterState;
import com.zupzup.untact.model.domain.store.Store;
import com.zupzup.untact.model.dto.response.DeleteStoreListRes;
import com.zupzup.untact.model.dto.response.StoreRes;
import com.zupzup.untact.repository.*;
import com.zupzup.untact.service.TerminationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.zupzup.untact.exception.store.StoreExceptionType.NO_MATCH_STORE;

@Service
@AllArgsConstructor
public class TerminationServiceImpl implements TerminationService {

    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final EnterRepository enterRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 회원 탈퇴 요청 리스트
     */
    @Override
    public List<DeleteStoreListRes> gatherDeletionRequests() {

        // enterState 가 delete 인 가게 리스트
        List<Store> storeList = storeRepository.findByEnterState(EnterState.DELETE);

        return storeList.stream()
                .map(entity -> modelMapper.map(entity, DeleteStoreListRes.class))
                .collect(Collectors.toList());
    }

    /**
     * 탈퇴 요청 상세
     */
    @Override
    public StoreRes deletionDetail(Long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));

        // 판매자 로그인 아이디 가져오기
        Seller seller = sellerRepository.findById(store.getSellerId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        StoreRes rs = modelMapper.map(store, StoreRes.class);
        rs.setSellerLoginId(seller.getLoginId());

        return rs;
    }

    /**
     * 탈퇴 -> 노출 대기
     */
    @Override
    public String deleteToConfirm(Long id) {

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));

        // 노출 대기 상태로 변경
        store.setEnterState(EnterState.WAIT);
        // 노출 대기된 시간 업데이트
        store.setWaitStatusTimestamp(timeSetter());

        // 회원 탈퇴 요청 철회
        Long sellerId = store.getSellerId();
        Seller seller = sellerRepository.findById(sellerId)
                        .orElseThrow(()-> new MemberException(NOT_FOUND_MEMBER));
        seller.setWantDeletion(false);

        storeRepository.save(store);
        sellerRepository.save(seller);

        return "Enter state is changed into WAIT";
    }

    /**
     * 탈퇴 승인
     */
    @Override
    @Transactional
    public String confirmDelete(Long storeId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(NO_MATCH_STORE));
        // seller unique ID 가져오기
        Long sellerId = store.getSellerId();
        // Member 찾기
        Member member = memberRepository.findBySellerId(sellerId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        // 가게를 찜한 사용자 목록 조회
        List<User> usersWithStarredStore = userRepository.findByStarredStoresContains(storeId);

        // 찜 목록에서 가게 ID 제거
        for (User user : usersWithStarredStore) {
            user.getStarredStores().remove(storeId);
            userRepository.save(user);
        }

        // 가게에 알림 설정한 사용자 목록 조회
        List<User> usersWithAlertStore = userRepository.findByAlertStoresContains(storeId);

        // 알림 목록에서 가게 ID 제거
        for (User user : usersWithAlertStore) {
            user.getAlertStores().remove(storeId);
            userRepository.save(user);
        }

        // 가게 삭제
        storeRepository.deleteById(storeId);
        // 유저 삭제
        sellerRepository.deleteById(sellerId);
        // 웹 신청서 삭제
        enterRepository.deleteByMember(member);
        // 웹 멤버 삭제
        memberRepository.deleteById(member.getId());

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
