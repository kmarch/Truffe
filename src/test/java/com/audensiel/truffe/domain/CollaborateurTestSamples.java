package com.audensiel.truffe.domain;

import java.util.UUID;

public class CollaborateurTestSamples {

    public static Collaborateur getCollaborateurSample1() {
        return new Collaborateur().id("id1").matricule("matricule1").diplome("diplome1").anglais("anglais1");
    }

    public static Collaborateur getCollaborateurSample2() {
        return new Collaborateur().id("id2").matricule("matricule2").diplome("diplome2").anglais("anglais2");
    }

    public static Collaborateur getCollaborateurRandomSampleGenerator() {
        return new Collaborateur()
            .id(UUID.randomUUID().toString())
            .matricule(UUID.randomUUID().toString())
            .diplome(UUID.randomUUID().toString())
            .anglais(UUID.randomUUID().toString());
    }
}
