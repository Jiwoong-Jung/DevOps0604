package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AttachGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AttachGroup getAttachGroupSample1() {
        return new AttachGroup().id(1L).createdBy(1L);
    }

    public static AttachGroup getAttachGroupSample2() {
        return new AttachGroup().id(2L).createdBy(2L);
    }

    public static AttachGroup getAttachGroupRandomSampleGenerator() {
        return new AttachGroup().id(longCount.incrementAndGet()).createdBy(longCount.incrementAndGet());
    }
}
