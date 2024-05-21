package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AttachGroupTestSamples.*;
import static com.mycompany.myapp.domain.AttachTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attach.class);
        Attach attach1 = getAttachSample1();
        Attach attach2 = new Attach();
        assertThat(attach1).isNotEqualTo(attach2);

        attach2.setId(attach1.getId());
        assertThat(attach1).isEqualTo(attach2);

        attach2 = getAttachSample2();
        assertThat(attach1).isNotEqualTo(attach2);
    }

    @Test
    void attachGroupTest() throws Exception {
        Attach attach = getAttachRandomSampleGenerator();
        AttachGroup attachGroupBack = getAttachGroupRandomSampleGenerator();

        attach.setAttachGroup(attachGroupBack);
        assertThat(attach.getAttachGroup()).isEqualTo(attachGroupBack);

        attach.attachGroup(null);
        assertThat(attach.getAttachGroup()).isNull();
    }
}
