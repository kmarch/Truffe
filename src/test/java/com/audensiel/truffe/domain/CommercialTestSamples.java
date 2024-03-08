package com.audensiel.truffe.domain;

import java.util.UUID;

public class CommercialTestSamples {

    public static Commercial getCommercialSample1() {
        return new Commercial().id("id1").matricule("matricule1");
    }

    public static Commercial getCommercialSample2() {
        return new Commercial().id("id2").matricule("matricule2");
    }

    public static Commercial getCommercialRandomSampleGenerator() {
        return new Commercial().id(UUID.randomUUID().toString()).matricule(UUID.randomUUID().toString());
    }
}
