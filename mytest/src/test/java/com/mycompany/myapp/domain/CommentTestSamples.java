package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CommentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Comment getCommentSample1() {
        return new Comment().id(1L).depth(1).comment("comment1").readCnt(1).goodCnt(1).badCnt(1).createdBy(1L).modifiedBy(1L);
    }

    public static Comment getCommentSample2() {
        return new Comment().id(2L).depth(2).comment("comment2").readCnt(2).goodCnt(2).badCnt(2).createdBy(2L).modifiedBy(2L);
    }

    public static Comment getCommentRandomSampleGenerator() {
        return new Comment()
            .id(longCount.incrementAndGet())
            .depth(intCount.incrementAndGet())
            .comment(UUID.randomUUID().toString())
            .readCnt(intCount.incrementAndGet())
            .goodCnt(intCount.incrementAndGet())
            .badCnt(intCount.incrementAndGet())
            .createdBy(longCount.incrementAndGet())
            .modifiedBy(longCount.incrementAndGet());
    }
}
