package org.example.test;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    // dumb service for convenience
    @Override
    public Integer add(int a, int b) {
        return a + b;
    }
}
