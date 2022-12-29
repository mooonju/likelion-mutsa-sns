package com.likelion.sns.controller;

import com.likelion.sns.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class HelloController {

    private final AlgorithmService algorithmService;
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("조문주");
    }

    @GetMapping("/hello/{num}")
    public Integer sumOfDigit(@PathVariable Integer num) {
        return algorithmService.sumOfDigit(num);
    }
}
