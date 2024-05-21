package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AttachTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Attach getAttachSample1() {
        return new Attach()
            .id(1L)
            .ord(1)
            .name("name1")
            .origName("origName1")
            .ext("ext1")
            .contentType("contentType1")
            .path("path1")
            .fileSize(1L)
            .createdBy(1L)
            .modifiedBy(1L);
    }

    public static Attach getAttachSample2() {
        return new Attach()
            .id(2L)
            .ord(2)
            .name("name2")
            .origName("origName2")
            .ext("ext2")
            .contentType("contentType2")
            .path("path2")
            .fileSize(2L)
            .createdBy(2L)
            .modifiedBy(2L);
    }

    public static Attach getAttachRandomSampleGenerator() {
        return new Attach()
            .id(longCount.incrementAndGet())
            .ord(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .origName(UUID.randomUUID().toString())
            .ext(UUID.randomUUID().toString())
            .contentType(UUID.randomUUID().toString())
            .path(UUID.randomUUID().toString())
            .fileSize(longCount.incrementAndGet())
            .createdBy(longCount.incrementAndGet())
            .modifiedBy(longCount.incrementAndGet());
    }
}
