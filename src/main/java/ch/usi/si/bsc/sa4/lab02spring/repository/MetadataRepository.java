package ch.usi.si.bsc.sa4.lab02spring.repository;

import ch.usi.si.bsc.sa4.lab02spring.model.Metadata.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Collection of all information for each level played for ach user.
 * Represents a collection of all level data for each user
 */
public interface MetadataRepository extends MongoRepository<Metadata,String>{

    Optional<Metadata>findByUsernameContaining(String username);

}
