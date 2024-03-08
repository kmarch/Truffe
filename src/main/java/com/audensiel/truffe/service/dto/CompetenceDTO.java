package com.audensiel.truffe.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.audensiel.truffe.domain.Competence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompetenceDTO implements Serializable {

    private String id;

    private String nom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetenceDTO)) {
            return false;
        }

        CompetenceDTO competenceDTO = (CompetenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, competenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetenceDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
