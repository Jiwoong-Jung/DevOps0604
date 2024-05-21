package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.AttachAsserts.*;
import static com.mycompany.myapp.domain.AttachTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachMapperTest {

    private AttachMapper attachMapper;

    @BeforeEach
    void setUp() {
        attachMapper = new AttachMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAttachSample1();
        var actual = attachMapper.toEntity(attachMapper.toDto(expected));
        assertAttachAllPropertiesEquals(expected, actual);
    }
}
