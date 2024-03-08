package com.audensiel.truffe.domain;

import static com.audensiel.truffe.domain.AgenceTestSamples.*;
import static com.audensiel.truffe.domain.CollaborateurTestSamples.*;
import static com.audensiel.truffe.domain.CompetenceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CollaborateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collaborateur.class);
        Collaborateur collaborateur1 = getCollaborateurSample1();
        Collaborateur collaborateur2 = new Collaborateur();
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);

        collaborateur2.setId(collaborateur1.getId());
        assertThat(collaborateur1).isEqualTo(collaborateur2);

        collaborateur2 = getCollaborateurSample2();
        assertThat(collaborateur1).isNotEqualTo(collaborateur2);
    }

    @Test
    void competenceTest() throws Exception {
        Collaborateur collaborateur = getCollaborateurRandomSampleGenerator();
        Competence competenceBack = getCompetenceRandomSampleGenerator();

        collaborateur.addCompetence(competenceBack);
        assertThat(collaborateur.getCompetences()).containsOnly(competenceBack);

        collaborateur.removeCompetence(competenceBack);
        assertThat(collaborateur.getCompetences()).doesNotContain(competenceBack);

        collaborateur.competences(new HashSet<>(Set.of(competenceBack)));
        assertThat(collaborateur.getCompetences()).containsOnly(competenceBack);

        collaborateur.setCompetences(new HashSet<>());
        assertThat(collaborateur.getCompetences()).doesNotContain(competenceBack);
    }

    @Test
    void nomAgenceTest() throws Exception {
        Collaborateur collaborateur = getCollaborateurRandomSampleGenerator();
        Agence agenceBack = getAgenceRandomSampleGenerator();

        collaborateur.setNomAgence(agenceBack);
        assertThat(collaborateur.getNomAgence()).isEqualTo(agenceBack);

        collaborateur.nomAgence(null);
        assertThat(collaborateur.getNomAgence()).isNull();
    }
}
