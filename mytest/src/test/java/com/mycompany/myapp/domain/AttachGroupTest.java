package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AttachGroupTestSamples.*;
import static com.mycompany.myapp.domain.AttachTestSamples.*;
import static com.mycompany.myapp.domain.PostTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AttachGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachGroup.class);
        AttachGroup attachGroup1 = getAttachGroupSample1();
        AttachGroup attachGroup2 = new AttachGroup();
        assertThat(attachGroup1).isNotEqualTo(attachGroup2);

        attachGroup2.setId(attachGroup1.getId());
        assertThat(attachGroup1).isEqualTo(attachGroup2);

        attachGroup2 = getAttachGroupSample2();
        assertThat(attachGroup1).isNotEqualTo(attachGroup2);
    }

    @Test
    void postTest() throws Exception {
        AttachGroup attachGroup = getAttachGroupRandomSampleGenerator();
        Post postBack = getPostRandomSampleGenerator();

        attachGroup.setPost(postBack);
        assertThat(attachGroup.getPost()).isEqualTo(postBack);

        attachGroup.post(null);
        assertThat(attachGroup.getPost()).isNull();
    }

    @Test
    void attachTest() throws Exception {
        AttachGroup attachGroup = getAttachGroupRandomSampleGenerator();
        Attach attachBack = getAttachRandomSampleGenerator();

        attachGroup.addAttach(attachBack);
        assertThat(attachGroup.getAttaches()).containsOnly(attachBack);
        assertThat(attachBack.getAttachGroup()).isEqualTo(attachGroup);

        attachGroup.removeAttach(attachBack);
        assertThat(attachGroup.getAttaches()).doesNotContain(attachBack);
        assertThat(attachBack.getAttachGroup()).isNull();

        attachGroup.attaches(new HashSet<>(Set.of(attachBack)));
        assertThat(attachGroup.getAttaches()).containsOnly(attachBack);
        assertThat(attachBack.getAttachGroup()).isEqualTo(attachGroup);

        attachGroup.setAttaches(new HashSet<>());
        assertThat(attachGroup.getAttaches()).doesNotContain(attachBack);
        assertThat(attachBack.getAttachGroup()).isNull();
    }
}
