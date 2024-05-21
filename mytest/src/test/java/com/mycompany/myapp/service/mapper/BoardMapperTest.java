package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.BoardAsserts.*;
import static com.mycompany.myapp.domain.BoardTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardMapperTest {

    private BoardMapper boardMapper;

    @BeforeEach
    void setUp() {
        boardMapper = new BoardMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBoardSample1();
        var actual = boardMapper.toEntity(boardMapper.toDto(expected));
        assertBoardAllPropertiesEquals(expected, actual);
    }
}
