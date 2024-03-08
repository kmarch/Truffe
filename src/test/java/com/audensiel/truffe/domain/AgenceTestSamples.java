package com.audensiel.truffe.domain;

import java.util.UUID;

public class AgenceTestSamples {

    public static Agence getAgenceSample1() {
        return new Agence().id("id1").nom("nom1");
    }

    public static Agence getAgenceSample2() {
        return new Agence().id("id2").nom("nom2");
    }

    public static Agence getAgenceRandomSampleGenerator() {
        return new Agence().id(UUID.randomUUID().toString()).nom(UUID.randomUUID().toString());
    }
}
