package com.audensiel.truffe.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.audensiel.truffe.domain.Collaborateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CollaborateurDTO implements Serializable {

    private String id;

    private String matricule;

    private String diplome;

    private Instant dateDispo;

    private String anglais;

    private Set<CompetenceDTO> competences = new HashSet<>();

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

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public Instant getDateDispo() {
        return dateDispo;
    }

    public void setDateDispo(Instant dateDispo) {
        this.dateDispo = dateDispo;
    }

    public String getAnglais() {
        return anglais;
    }

    public void setAnglais(String anglais) {
        this.anglais = anglais;
    }

    public Set<CompetenceDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(Set<CompetenceDTO> competences) {
        this.competences = competences;
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
        if (!(o instanceof CollaborateurDTO)) {
            return false;
        }

        CollaborateurDTO collaborateurDTO = (CollaborateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, collaborateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollaborateurDTO{" +
            "id='" + getId() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", dateDispo='" + getDateDispo() + "'" +
            ", anglais='" + getAnglais() + "'" +
            ", competences=" + getCompetences() +
            ", nomAgence=" + getNomAgence() +
            "}";
    }
}
