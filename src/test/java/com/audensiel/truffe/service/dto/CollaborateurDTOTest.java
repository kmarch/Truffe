package com.audensiel.truffe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollaborateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollaborateurDTO.class);
        CollaborateurDTO collaborateurDTO1 = new CollaborateurDTO();
        collaborateurDTO1.setId("id1");
        CollaborateurDTO collaborateurDTO2 = new CollaborateurDTO();
        assertThat(collaborateurDTO1).isNotEqualTo(collaborateurDTO2);
        collaborateurDTO2.setId(collaborateurDTO1.getId());
        assertThat(collaborateurDTO1).isEqualTo(collaborateurDTO2);
        collaborateurDTO2.setId("id2");
        assertThat(collaborateurDTO1).isNotEqualTo(collaborateurDTO2);
        collaborateurDTO1.setId(null);
        assertThat(collaborateurDTO1).isNotEqualTo(collaborateurDTO2);
    }
}
