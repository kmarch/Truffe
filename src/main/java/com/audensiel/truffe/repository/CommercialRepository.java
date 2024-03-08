package com.audensiel.truffe.repository;

import com.audensiel.truffe.domain.Commercial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Commercial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommercialRepository extends MongoRepository<Commercial, String> {}
