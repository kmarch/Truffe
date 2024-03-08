package com.audensiel.truffe.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.audensiel.truffe.domain.Commercial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommercialDTO implements Serializable {

    private String id;

    private String matricule;

    private AgenceDTO nomAgence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public AgenceDTO getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(AgenceDTO nomAgence) {
        this.nomAgence = nomAgence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommercialDTO)) {
            return false;
        }

        CommercialDTO commercialDTO = (CommercialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commercialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommercialDTO{" +
            "id='" + getId() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", nomAgence=" + getNomAgence() +
            "}";
    }
}
