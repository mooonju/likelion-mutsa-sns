package com.likelion.sns.service;


import com.likelion.sns.domaion.dto.alarm.AlarmResponse;
import com.likelion.sns.domaion.entity.Alarm;
import com.likelion.sns.domaion.entity.User;
import com.likelion.sns.exception.AppException;
import com.likelion.sns.exception.ErrorCode;
import com.likelion.sns.repository.AlarmRepository;
import com.likelion.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public Page<AlarmResponse> alarm(Pageable pageable, String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new AppException(ErrorCode.USERNAME_NOT_FOUND));

        Page<Alarm> alarmList = alarmRepository.findAllByUser(pageable, user);
        Page<AlarmResponse> alarmResponses = AlarmResponse.alarmList(alarmList);

        return alarmResponses;
    }
}
