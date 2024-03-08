package com.audensiel.truffe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Agence.
 */
@Document(collection = "agence")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @DBRef
    @Field("commercial")
    @JsonIgnoreProperties(value = { "nomAgence" }, allowSetters = true)
    private Set<Commercial> commercials = new HashSet<>();

    @DBRef
    @Field("collaborateur")
    @JsonIgnoreProperties(value = { "competences", "nomAgence" }, allowSetters = true)
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Agence id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Agence nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Commercial> getCommercials() {
        return this.commercials;
    }

    public void setCommercials(Set<Commercial> commercials) {
        if (this.commercials != null) {
            this.commercials.forEach(i -> i.setNomAgence(null));
        }
        if (commercials != null) {
            commercials.forEach(i -> i.setNomAgence(this));
        }
        this.commercials = commercials;
    }

    public Agence commercials(Set<Commercial> commercials) {
        this.setCommercials(commercials);
        return this;
    }

    public Agence addCommercial(Commercial commercial) {
        this.commercials.add(commercial);
        commercial.setNomAgence(this);
        return this;
    }

    public Agence removeCommercial(Commercial commercial) {
        this.commercials.remove(commercial);
        commercial.setNomAgence(null);
        return this;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.setNomAgence(null));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.setNomAgence(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Agence collaborateurs(Set<Collaborateur> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Agence addCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.add(collaborateur);
        collaborateur.setNomAgence(this);
        return this;
    }

    public Agence removeCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.remove(collaborateur);
        collaborateur.setNomAgence(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return getId() != null && getId().equals(((Agence) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
