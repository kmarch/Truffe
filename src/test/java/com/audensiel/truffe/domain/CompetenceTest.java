package com.audensiel.truffe.domain;

import static com.audensiel.truffe.domain.CollaborateurTestSamples.*;
import static com.audensiel.truffe.domain.CompetenceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.audensiel.truffe.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CompetenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competence.class);
        Competence competence1 = getCompetenceSample1();
        Competence competence2 = new Competence();
        assertThat(competence1).isNotEqualTo(competence2);

        competence2.setId(competence1.getId());
        assertThat(competence1).isEqualTo(competence2);

        competence2 = getCompetenceSample2();
        assertThat(competence1).isNotEqualTo(competence2);
    }

    @Test
    void collaborateurTest() throws Exception {
        Competence competence = getCompetenceRandomSampleGenerator();
        Collaborateur collaborateurBack = getCollaborateurRandomSampleGenerator();

        competence.addCollaborateur(collaborateurBack);
        assertThat(competence.getCollaborateurs()).containsOnly(collaborateurBack);
        assertThat(collaborateurBack.getCompetences()).containsOnly(competence);

        competence.removeCollaborateur(collaborateurBack);
        assertThat(competence.getCollaborateurs()).doesNotContain(collaborateurBack);
        assertThat(collaborateurBack.getCompetences()).doesNotContain(competence);

        competence.collaborateurs(new HashSet<>(Set.of(collaborateurBack)));
        assertThat(competence.getCollaborateurs()).containsOnly(collaborateurBack);
        assertThat(collaborateurBack.getCompetences()).containsOnly(competence);

        competence.setCollaborateurs(new HashSet<>());
        assertThat(competence.getCollaborateurs()).doesNotContain(collaborateurBack);
        assertThat(collaborateurBack.getCompetences()).doesNotContain(competence);
    }
}
