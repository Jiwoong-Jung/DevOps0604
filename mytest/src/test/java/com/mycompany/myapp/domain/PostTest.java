package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AttachGroupTestSamples.*;
import static com.mycompany.myapp.domain.BoardTestSamples.*;
import static com.mycompany.myapp.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);

        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }

    @Test
    void boardTest() throws Exception {
        Post post = getPostRandomSampleGenerator();
        Board boardBack = getBoardRandomSampleGenerator();

        post.setBoard(boardBack);
        assertThat(post.getBoard()).isEqualTo(boardBack);

        post.board(null);
        assertThat(post.getBoard()).isNull();
    }

    @Test
    void attachGroupTest() throws Exception {
        Post post = getPostRandomSampleGenerator();
        AttachGroup attachGroupBack = getAttachGroupRandomSampleGenerator();

        post.setAttachGroup(attachGroupBack);
        assertThat(post.getAttachGroup()).isEqualTo(attachGroupBack);
        assertThat(attachGroupBack.getPost()).isEqualTo(post);

        post.attachGroup(null);
        assertThat(post.getAttachGroup()).isNull();
        assertThat(attachGroupBack.getPost()).isNull();
    }
}
