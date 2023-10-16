package com.zupzup.untact.service.impl;

import com.zupzup.untact.exception.enter.EnterException;
import com.zupzup.untact.model.Enter;
import com.zupzup.untact.model.Manager;
import com.zupzup.untact.model.auth.ManagerAuthority;
import com.zupzup.untact.model.dto.request.ManagerReq;
import com.zupzup.untact.model.dto.response.ManagerRes;
import com.zupzup.untact.model.response.EnterApprovalRes;
import com.zupzup.untact.repository.EnterRepository;
import com.zupzup.untact.repository.ManagerRepository;
import com.zupzup.untact.service.BaseServiceImpl;
import com.zupzup.untact.service.ManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static com.zupzup.untact.exception.enter.EnterExceptionType.NO_MATCH_ENTER;

@Service
public class ManagerServiceImpl extends BaseServiceImpl<Manager, ManagerReq, ManagerRes, ManagerRepository> implements ManagerService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ManagerRepository managerRepository;
    private final EnterRepository enterRepository;

    public ManagerServiceImpl(ManagerRepository repository, ManagerRepository managerRepository, EnterRepository enterRepository) {
        super(repository);
        this.managerRepository = managerRepository;
        this.enterRepository = enterRepository;
    }

    /**
     * 매니저 아이디 생성 및 저장
     */
    @Override
    public ManagerRes save(ManagerReq rq) {

        // 패스워드 인코딩 후 저장
        Manager m = Manager.builder()
                .created_at(timeSetter())
                .name(rq.getName())
                .loginId(rq.getLoginId())
                .loginPwd(passwordEncoder.encode(rq.getLoginPwd()))
                .build();

        m.setManagerRoles(
                Collections.singletonList(ManagerAuthority.builder()
                        .name("ROLE_MANAGER")
                        .manager(m)
                        .build())

        );
        managerRepository.save(m);

        return modelMapper.map(m, ManagerRes.class);
    }

    /**
     * 매니저 관련 내용 수정
     */
    @Override
    public ManagerRes update(Long id, ManagerReq rq) {

        Manager m = managerRepository.findById(rq.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 사용자는 존재하지 않습니다."));

        // 업데이트 후 저장
        m.updateManager(rq, passwordEncoder);
        managerRepository.save(m);

        return modelMapper.map(m, ManagerRes.class);
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
