package com.audensiel.truffe.domain;

import java.util.UUID;

public class CompetenceTestSamples {

    public static Competence getCompetenceSample1() {
        return new Competence().id("id1").nom("nom1");
    }

    public static Competence getCompetenceSample2() {
        return new Competence().id("id2").nom("nom2");
    }

    public static Competence getCompetenceRandomSampleGenerator() {
        return new Competence().id(UUID.randomUUID().toString()).nom(UUID.randomUUID().toString());
    }
}
