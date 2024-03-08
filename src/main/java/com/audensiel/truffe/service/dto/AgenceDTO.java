package com.audensiel.truffe.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.audensiel.truffe.domain.Agence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenceDTO implements Serializable {

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
        if (!(o instanceof AgenceDTO)) {
            return false;
        }

        AgenceDTO agenceDTO = (AgenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, agenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
