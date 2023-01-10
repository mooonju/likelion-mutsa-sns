package com.likelion.sns.domaion.dto.alarm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.sns.domaion.entity.Alarm;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static Page<AlarmResponse> alarmList(Page<Alarm> alarm) {
        Page<AlarmResponse> alarmResponses = alarm.map((alarms -> AlarmResponse.builder()
                .id(alarms.getId())
                .alarmType(alarms.getAlarmType())
                .fromUserId(alarms.getFromUserId())
                .targetId(alarms.getTargetId())
                .text(alarms.getText())
                .createdAt(alarms.getCreatedAt())
                .build()));
        return alarmResponses;
    }
}
