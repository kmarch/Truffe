package com.audensiel.truffe.domain;

import static com.audensiel.truffe.domain.AgenceTestSamples.*;
import static com.audensiel.truffe.domain.CollaborateurTestSamples.*;
import static com.audensiel.truffe.domain.CommercialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agence.class);
        Agence agence1 = getAgenceSample1();
        Agence agence2 = new Agence();
        assertThat(agence1).isNotEqualTo(agence2);

        agence2.setId(agence1.getId());
        assertThat(agence1).isEqualTo(agence2);

        agence2 = getAgenceSample2();
        assertThat(agence1).isNotEqualTo(agence2);
    }

    @Test
    void commercialTest() throws Exception {
        Agence agence = getAgenceRandomSampleGenerator();
        Commercial commercialBack = getCommercialRandomSampleGenerator();

        agence.addCommercial(commercialBack);
        assertThat(agence.getCommercials()).containsOnly(commercialBack);
        assertThat(commercialBack.getNomAgence()).isEqualTo(agence);

        agence.removeCommercial(commercialBack);
        assertThat(agence.getCommercials()).doesNotContain(commercialBack);
        assertThat(commercialBack.getNomAgence()).isNull();

        agence.commercials(new HashSet<>(Set.of(commercialBack)));
        assertThat(agence.getCommercials()).containsOnly(commercialBack);
        assertThat(commercialBack.getNomAgence()).isEqualTo(agence);

        agence.setCommercials(new HashSet<>());
        assertThat(agence.getCommercials()).doesNotContain(commercialBack);
        assertThat(commercialBack.getNomAgence()).isNull();
    }

    @Test
    void collaborateurTest() throws Exception {
        Agence agence = getAgenceRandomSampleGenerator();
        Collaborateur collaborateurBack = getCollaborateurRandomSampleGenerator();

        agence.addCollaborateur(collaborateurBack);
        assertThat(agence.getCollaborateurs()).containsOnly(collaborateurBack);
        assertThat(collaborateurBack.getNomAgence()).isEqualTo(agence);

        agence.removeCollaborateur(collaborateurBack);
        assertThat(agence.getCollaborateurs()).doesNotContain(collaborateurBack);
        assertThat(collaborateurBack.getNomAgence()).isNull();

        agence.collaborateurs(new HashSet<>(Set.of(collaborateurBack)));
        assertThat(agence.getCollaborateurs()).containsOnly(collaborateurBack);
        assertThat(collaborateurBack.getNomAgence()).isEqualTo(agence);

        agence.setCollaborateurs(new HashSet<>());
        assertThat(agence.getCollaborateurs()).doesNotContain(collaborateurBack);
        assertThat(collaborateurBack.getNomAgence()).isNull();
    }
}
