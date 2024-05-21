package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachGroupDTO.class);
        AttachGroupDTO attachGroupDTO1 = new AttachGroupDTO();
        attachGroupDTO1.setId(1L);
        AttachGroupDTO attachGroupDTO2 = new AttachGroupDTO();
        assertThat(attachGroupDTO1).isNotEqualTo(attachGroupDTO2);
        attachGroupDTO2.setId(attachGroupDTO1.getId());
        assertThat(attachGroupDTO1).isEqualTo(attachGroupDTO2);
        attachGroupDTO2.setId(2L);
        assertThat(attachGroupDTO1).isNotEqualTo(attachGroupDTO2);
        attachGroupDTO1.setId(null);
        assertThat(attachGroupDTO1).isNotEqualTo(attachGroupDTO2);
    }
}
