package com.company.services;

import com.company.entity.AuthUser;
import com.company.entity.UserSMS;
import com.company.repositories.UserSMSRepository;
import com.company.utils.BaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SMSService {
    private final UserSMSRepository userSMSRepository;
    private final BaseUtil baseUtil;

    public UserSMS createSMS(AuthUser user) {
        Integer userId = user.getId();
        UserSMS userSMS = findByUserId(userId);
        if (!Objects.isNull(userSMS)) {
            return userSMS;
        }
        Integer code = baseUtil.generateCode();
        UserSMS sms = UserSMS.builder()
                .userId(userId)
                .code(code)
                .build();
        userSMS = userSMSRepository.save(sms);
        log.info("SMS: {}",code);
        return userSMS;
    }
    public UserSMS update(UserSMS userSMS) {
        return userSMSRepository.save(userSMS);
    }
    public UserSMS findByUserId(Integer userId) {
        return userSMSRepository.findByUserId(userId);
    }
}
