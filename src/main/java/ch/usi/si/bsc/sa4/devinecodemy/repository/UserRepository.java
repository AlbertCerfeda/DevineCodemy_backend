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
    // You can implement complex "predefined" logic with specific conventions by specific method names
    // Documentation link: https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries
    List<User> findAllByNameContainingAndPublicProfileTrue(String string);
    List<User> findAllByNameContainingAndPublicProfileFalse(String string);

    @Query("{'publicProfile':true}")
    List<User> findAllPublic();
    
    @Query(value="{ id : ?0}", fields="{ publicProfile : 1 }")
    Optional<User> isUserPublic(String id);

    Boolean existsByName(String name);

    
    
    
}
