package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PostTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Post getPostSample1() {
        return new Post().id(1L).status("status1").title("title1").readCnt(1).goodCnt(1).badCnt(1).createdBy(1L).modifiedBy(1L);
    }

    public static Post getPostSample2() {
        return new Post().id(2L).status("status2").title("title2").readCnt(2).goodCnt(2).badCnt(2).createdBy(2L).modifiedBy(2L);
    }

    public static Post getPostRandomSampleGenerator() {
        return new Post()
            .id(longCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .readCnt(intCount.incrementAndGet())
            .goodCnt(intCount.incrementAndGet())
            .badCnt(intCount.incrementAndGet())
            .createdBy(longCount.incrementAndGet())
            .modifiedBy(longCount.incrementAndGet());
    }
}
