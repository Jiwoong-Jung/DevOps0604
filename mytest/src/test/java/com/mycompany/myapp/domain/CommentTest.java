package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CommentTestSamples.*;
import static com.mycompany.myapp.domain.CommentTestSamples.*;
import static com.mycompany.myapp.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    void commentTest() throws Exception {
        Comment comment = getCommentRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        comment.addComment(commentBack);
        assertThat(comment.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getParent()).isEqualTo(comment);

        comment.removeComment(commentBack);
        assertThat(comment.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getParent()).isNull();

        comment.comments(new HashSet<>(Set.of(commentBack)));
        assertThat(comment.getComments()).containsOnly(commentBack);
        assertThat(commentBack.getParent()).isEqualTo(comment);

        comment.setComments(new HashSet<>());
        assertThat(comment.getComments()).doesNotContain(commentBack);
        assertThat(commentBack.getParent()).isNull();
    }

    @Test
    void postTest() throws Exception {
        Comment comment = getCommentRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        comment.setPost(postBack);
        assertThat(comment.getPost()).isEqualTo(postBack);

        comment.post(null);
        assertThat(comment.getPost()).isNull();
    }

    @Test
    void parentTest() throws Exception {
        Comment comment = getCommentRandomSampleGenerator();
        Comment commentBack = getCommentRandomSampleGenerator();

        comment.setParent(commentBack);
        assertThat(comment.getParent()).isEqualTo(commentBack);

        comment.parent(null);
        assertThat(comment.getParent()).isNull();
    }
}
