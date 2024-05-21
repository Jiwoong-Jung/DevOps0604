package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.AttachGroupAsserts.*;
import static com.mycompany.myapp.domain.AttachGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttachGroupMapperTest {

    private AttachGroupMapper attachGroupMapper;

    @BeforeEach
    void setUp() {
        attachGroupMapper = new AttachGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAttachGroupSample1();
        var actual = attachGroupMapper.toEntity(attachGroupMapper.toDto(expected));
        assertAttachGroupAllPropertiesEquals(expected, actual);
    }
}
