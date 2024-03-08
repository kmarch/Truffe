package com.audensiel.truffe.repository;

import com.audensiel.truffe.domain.Agence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Agence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceRepository extends MongoRepository<Agence, String> {}
