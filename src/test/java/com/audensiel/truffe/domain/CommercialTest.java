package com.audensiel.truffe.domain;

import static com.audensiel.truffe.domain.AgenceTestSamples.*;
import static com.audensiel.truffe.domain.CommercialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommercialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commercial.class);
        Commercial commercial1 = getCommercialSample1();
        Commercial commercial2 = new Commercial();
        assertThat(commercial1).isNotEqualTo(commercial2);

        commercial2.setId(commercial1.getId());
        assertThat(commercial1).isEqualTo(commercial2);

        commercial2 = getCommercialSample2();
        assertThat(commercial1).isNotEqualTo(commercial2);
    }

    @Test
    void nomAgenceTest() throws Exception {
        Commercial commercial = getCommercialRandomSampleGenerator();
        Agence agenceBack = getAgenceRandomSampleGenerator();

        commercial.setNomAgence(agenceBack);
        assertThat(commercial.getNomAgence()).isEqualTo(agenceBack);

        commercial.nomAgence(null);
        assertThat(commercial.getNomAgence()).isNull();
    }
}
