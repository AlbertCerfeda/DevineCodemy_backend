package ch.usi.si.bsc.sa4.devinecodemy.repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;

import java.util.Optional;

/**
 * The LevelRepository class represents the collection and grouping
 * of all 'Level' database objects.
 */
@Repository
public interface LevelRepository extends MongoRepository<Level, String> {

    /**
     * Retrieves an Optional of the level with the given levelNumber.
     * @param levelNumber the number of the Level to search in the db.
     * @return the Optional<Level> containing:
     *  - the level if found
     *  - an empty Optional if level not found.
     */
    Optional<Level> findByLevelNumber(int levelNumber);

    /**
     * Returns whether the Level with the given levelNumber exists or not.
     *
     * @param levelNumber the levelNumber of the Level to seek.
     * @return a boolean telling whether the level exists or not.
     */
    boolean existsByLevelNumber(int levelNumber);

    /**
     * Deletes the Level with the given levelNumber in the db.
     * @param levelNumber the number of the Level to be deleted.
     */
    void deleteByLevelNumber(int levelNumber);
    
    /**
     * Returns the first level for a specific EWorld.
     * @param world the EWorld of the level.
     * @return the first level for a specific EWorld.
     */
    Optional<Level> findFirstByLevelWorldOrderByLevelNumberAsc(EWorld world);
    
    /**
     * Returns the last level for a specific EWorld.
     * @param world the EWorld of the level.
     * @return the last level for a specific EWorld.
     */
    Optional<Level> findFirstByLevelWorldOrderByLevelNumberDesc(EWorld world);
}
