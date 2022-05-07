package ch.usi.si.bsc.sa4.devinecodemy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;

import java.util.List;
import java.util.Optional;

/**
 * The UserRepository class represents the collection and grouping
 * of all 'User' database objects.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Returns a List of Users with the given name
     * and with a public profile.
     * @param string the name to seek into the db.
     * @return the List<User> result of the search in the db.
     */
    List<User> findAllByNameContainingAndPublicProfileTrue(String string);

    /**
     * Returns a List of Users with the given name
     * and with a private profile.
     * @param string the name to seek into the db.
     * @return the List<User> result of the search in the db.
     */
    List<User> findAllByNameContainingAndPublicProfileFalse(String string);

    /**
     * Returns a List of Users with a public profile.
     * @return the List<User> result of the search in the db.
     */
    @Query("{'publicProfile':true}")
    List<User> findAllPublic();
    
    /**
     * Returns an Optional containing:
     *  - The User if it's public
     *  - nothing if the user with the given id is private.
     * @param id the id of the user to seek in the db.
     * @return the Optional<User> result of the search in the db.
     */
    @Query(value="{ id : ?0}", fields="{ publicProfile : 1 }")
    Optional<User> isUserPublic(String id);

    /**
     * Returns whether a user with the given name exists or not.
     * @param name the name of the user to seek in the db.
     * @return the boolean result of the search in the db.
     */
    Boolean existsByName(String name);

    
    
    
}
