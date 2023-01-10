package com.likelion.sns.domaion.entity;

import com.likelion.sns.domaion.dto.alarm.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Alarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private Long fromUserId;
    private Long targetId;
    private String text;



    public static Alarm of(User user, AlarmType alarmType, Long fromUserId, Long targetId) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .alarmType(alarmType)
                .fromUserId(fromUserId)
                .targetId(targetId)
                .build();

        return alarm;
    }
}
