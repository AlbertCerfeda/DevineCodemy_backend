package ch.usi.si.bsc.sa4.lab02spring.repository;

import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represents the collection and grouping of all 'Level' database objects.
 */
@Repository
public interface LevelRepository extends MongoRepository<Level, String> {
    
    @Query(fields="{ board : 0 }")
    List<Level> findAllInfo();
    
    
}
