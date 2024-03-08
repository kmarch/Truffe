package com.audensiel.truffe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Collaborateur.
 */
@Document(collection = "collaborateur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collaborateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("matricule")
    private String matricule;

    @Field("diplome")
    private String diplome;

    @Field("date_dispo")
    private Instant dateDispo;

    @Field("anglais")
    private String anglais;

    @DBRef
    @Field("competences")
    @JsonIgnoreProperties(value = { "collaborateurs" }, allowSetters = true)
    private Set<Competence> competences = new HashSet<>();

    @DBRef
    @Field("nomAgence")
    @JsonIgnoreProperties(value = { "commercials", "collaborateurs" }, allowSetters = true)
    private Agence nomAgence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Collaborateur id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Collaborateur matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDiplome() {
        return this.diplome;
    }

    public Collaborateur diplome(String diplome) {
        this.setDiplome(diplome);
        return this;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public Instant getDateDispo() {
        return this.dateDispo;
    }

    public Collaborateur dateDispo(Instant dateDispo) {
        this.setDateDispo(dateDispo);
        return this;
    }

    public void setDateDispo(Instant dateDispo) {
        this.dateDispo = dateDispo;
    }

    public String getAnglais() {
        return this.anglais;
    }

    public Collaborateur anglais(String anglais) {
        this.setAnglais(anglais);
        return this;
    }

    public void setAnglais(String anglais) {
        this.anglais = anglais;
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    public Collaborateur competences(Set<Competence> competences) {
        this.setCompetences(competences);
        return this;
    }

    public Collaborateur addCompetence(Competence competence) {
        this.competences.add(competence);
        return this;
    }

    public Collaborateur removeCompetence(Competence competence) {
        this.competences.remove(competence);
        return this;
    }

    public Agence getNomAgence() {
        return this.nomAgence;
    }

    public void setNomAgence(Agence agence) {
        this.nomAgence = agence;
    }

    public Collaborateur nomAgence(Agence agence) {
        this.setNomAgence(agence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborateur)) {
            return false;
        }
        return getId() != null && getId().equals(((Collaborateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborateur{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", dateDispo='" + getDateDispo() + "'" +
            ", anglais='" + getAnglais() + "'" +
            "}";
    }
}
