package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BoardTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Board getBoardSample1() {
        return new Board().id(1L).title("title1").category("category1").createdBy(1L).modifiedBy(1L);
    }

    public static Board getBoardSample2() {
        return new Board().id(2L).title("title2").category("category2").createdBy(2L).modifiedBy(2L);
    }

    public static Board getBoardRandomSampleGenerator() {
        return new Board()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .category(UUID.randomUUID().toString())
            .createdBy(longCount.incrementAndGet())
            .modifiedBy(longCount.incrementAndGet());
    }
}
