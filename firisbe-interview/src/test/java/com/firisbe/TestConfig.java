package com.firisbe;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public Pageable pageable() {
        return PageRequest.of(0, 10);  // Customize page number and size as needed
    }
}