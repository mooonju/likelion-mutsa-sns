package com.likelion.sns.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("조문주");
    }

    @GetMapping("/hello/{num}")
    public ResponseEntity<Integer> sumOfDigit(@PathVariable Integer num) {
        int answer = 0;
        String str = Integer.toString(num);

        for (int i = 0; i < str.length(); i++) {
            answer += Integer.parseInt(str.substring(i, i + 1));
        }

        return ResponseEntity.ok().body(answer);
    }
}
