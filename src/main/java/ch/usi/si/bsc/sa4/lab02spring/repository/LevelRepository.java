package ch.usi.si.bsc.sa4.lab02spring.repository;

import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represents the collection and grouping of all 'Level' database objects.
 */
@Repository
public interface LevelRepository extends MongoRepository<Level, String> {
    // You can implement complex "predefined" logic with specific conventions by specific method names
    // Documentation link: https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries
    List<Level> findAllByNameContaining(String string);
}
