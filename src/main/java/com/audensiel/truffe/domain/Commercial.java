package com.audensiel.truffe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Commercial.
 */
@Document(collection = "commercial")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commercial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("matricule")
    private String matricule;

    @DBRef
    @Field("nomAgence")
    @JsonIgnoreProperties(value = { "commercials", "collaborateurs" }, allowSetters = true)
    private Agence nomAgence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Commercial id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Commercial matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Agence getNomAgence() {
        return this.nomAgence;
    }

    public void setNomAgence(Agence agence) {
        this.nomAgence = agence;
    }

    public Commercial nomAgence(Agence agence) {
        this.setNomAgence(agence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commercial)) {
            return false;
        }
        return getId() != null && getId().equals(((Commercial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commercial{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            "}";
    }
}
