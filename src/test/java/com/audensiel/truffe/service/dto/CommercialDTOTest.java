package com.audensiel.truffe.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommercialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialDTO.class);
        CommercialDTO commercialDTO1 = new CommercialDTO();
        commercialDTO1.setId("id1");
        CommercialDTO commercialDTO2 = new CommercialDTO();
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
        commercialDTO2.setId(commercialDTO1.getId());
        assertThat(commercialDTO1).isEqualTo(commercialDTO2);
        commercialDTO2.setId("id2");
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
        commercialDTO1.setId(null);
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
    }
}
