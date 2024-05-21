package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Board.class);
        Board board1 = getBoardSample1();
        Board board2 = new Board();
        assertThat(board1).isNotEqualTo(board2);

        board2.setId(board1.getId());
        assertThat(board1).isEqualTo(board2);

        board2 = getBoardSample2();
        assertThat(board1).isNotEqualTo(board2);
    }

    @Test
    void postTest() throws Exception {
        Board board = getBoardRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        board.addPost(postBack);
        assertThat(board.getPosts()).containsOnly(postBack);
        assertThat(postBack.getBoard()).isEqualTo(board);

        board.removePost(postBack);
        assertThat(board.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getBoard()).isNull();

        board.posts(new HashSet<>(Set.of(postBack)));
        assertThat(board.getPosts()).containsOnly(postBack);
        assertThat(postBack.getBoard()).isEqualTo(board);

        board.setPosts(new HashSet<>());
        assertThat(board.getPosts()).doesNotContain(postBack);
        assertThat(postBack.getBoard()).isNull();
    }
}
