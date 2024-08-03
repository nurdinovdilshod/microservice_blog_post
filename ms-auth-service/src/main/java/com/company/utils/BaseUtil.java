package com.company.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class BaseUtil {
    private final Random random;

    public Integer generateCode() {
        return random.nextInt(1_000, 9_999);
    }
}
