package com.likelion.sns.repository;

import com.likelion.sns.domaion.entity.Alarm;
import com.likelion.sns.domaion.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findAllByUser(Pageable pageable, User user);
}
