package com.zupzup.untact.service.impl;

import com.zupzup.untact.domain.enums.EnterState;
import com.zupzup.untact.exception.member.MemberException;
import com.zupzup.untact.exception.store.StoreException;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Member;
import com.zupzup.untact.model.request.EnterReq;
import com.zupzup.untact.model.response.EnterRes;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.repository.MemberRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.EnterService;
import org.apache.catalina.Store;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.zupzup.untact.exception.member.MemberExceptionType.NOT_FOUND_MEMBER;
import static com.zupzup.untact.exception.store.StoreExceptionType.CANNOT_APPLY_TWICE;

@Service
public class EnterServiceImpl extends BaseServiceImpl<Enter, EnterReq, EnterRes, EnterRepository> implements EnterService {

    @Autowired
    ModelMapper modelMapper;

    private final MemberRepository memberRepository;
    private final EnterRepository enterRepository;

    public EnterServiceImpl(EnterRepository repository, MemberRepository memberRepository, EnterRepository enterRepository) {
        super(repository);
        this.memberRepository = memberRepository;
        this.enterRepository = enterRepository;
    }

    /**
     * 가게 내용 저장
     */
    @Override
    public EnterRes save(EnterReq rq) {

        // 멤버 찾지 못하면 에러 발생
        Member member = memberRepository.findById(rq.getId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        // 입점 신청 횟수 확인
        if (member.getCnt() == 1) {

            throw new StoreException(CANNOT_APPLY_TWICE);
        }

        // 사장님과 입점 신청은 1:N 관계
        // 입점 신청에서 받은 내용들 저장 + 문의 상태는 NEW 로 설정 (관리자용)
        Enter enter = Enter.builder()
                .created_at(timeSetter())
                .member(member)
                .name(rq.getName())
                .phoneNum(rq.getPhoneNum())
                .storeNum("")
                .storeName(rq.getStoreName())
                .storeAddress(rq.getStoreAddress())
                .crNumber(rq.getCrNumber())
                .longitude(rq.getLongitude())
                .latitude(rq.getLatitude())
                .state(EnterState.NEW)
                .build();

        enterRepository.save(enter);

        // 신청 횟수 한 번 올리기
        member.setCnt(1);
        memberRepository.save(member);

        return modelMapper.map(enter, EnterRes.class);
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
