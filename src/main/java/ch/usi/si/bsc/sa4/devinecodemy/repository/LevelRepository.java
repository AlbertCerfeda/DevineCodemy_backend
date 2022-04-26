package ch.usi.si.bsc.sa4.devinecodemy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;

import java.util.List;
import java.util.Optional;

/**
 * Represents the collection and grouping of all 'Level' database objects.
 */
@Repository
public interface LevelRepository extends MongoRepository<Level, String> {
    
    @Query(value="{}",fields="{ board : 0 }")
    List<Level> findAllInfo();


    Optional<Level> findByNameContaining(String name);
    
}
