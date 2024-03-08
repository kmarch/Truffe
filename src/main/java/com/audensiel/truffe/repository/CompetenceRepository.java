package com.audensiel.truffe.repository;

import com.audensiel.truffe.domain.Competence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Competence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenceRepository extends MongoRepository<Competence, String> {}
