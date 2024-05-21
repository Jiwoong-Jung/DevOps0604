package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttachDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachDTO.class);
        AttachDTO attachDTO1 = new AttachDTO();
        attachDTO1.setId(1L);
        AttachDTO attachDTO2 = new AttachDTO();
        assertThat(attachDTO1).isNotEqualTo(attachDTO2);
        attachDTO2.setId(attachDTO1.getId());
        assertThat(attachDTO1).isEqualTo(attachDTO2);
        attachDTO2.setId(2L);
        assertThat(attachDTO1).isNotEqualTo(attachDTO2);
        attachDTO1.setId(null);
        assertThat(attachDTO1).isNotEqualTo(attachDTO2);
    }
}
