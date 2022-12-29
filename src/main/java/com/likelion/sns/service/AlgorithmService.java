package com.likelion.sns.service;

import org.springframework.stereotype.Service;

@Service
public class AlgorithmService {

    public Integer sumOfDigit(Integer num) {
        int answer = 0;
        String str = Integer.toString(num);

        for (int i = 0; i < str.length(); i++) {
            answer += Integer.parseInt(str.substring(i, i + 1));
        }

        return answer;
    }
}
