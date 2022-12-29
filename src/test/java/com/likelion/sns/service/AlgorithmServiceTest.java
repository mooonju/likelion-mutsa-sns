package com.likelion.sns.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
// Annotation 없이 만듭니다.
class AlgorithmServiceTest {

    // 실제 로직은 Service Test에서 검증 합니다.
    // Service Layer는 Spring없이도 돌아가게 최대한 만듭니다.
    // Spring을 안쓰고 테스트 하기 때문에 new를 이용해 초기화를 해줍니다.
    // Pojo방식을 최대한 활용합니다.

    AlgorithmService algorithmService = new AlgorithmService();

    @Test
    void sumOfDigit() {
        assertEquals(21, algorithmService.sumOfDigit(687));
        assertEquals(22, algorithmService.sumOfDigit(787));
        assertEquals(0, algorithmService.sumOfDigit(0));
        assertEquals(5, algorithmService.sumOfDigit(11111));

    }

}