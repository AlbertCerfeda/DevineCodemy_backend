package ch.usi.si.bsc.sa4.lab02spring.repository;

import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Represents the collection and grouping of all 'User' database objects.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // You can implement complex "predefined" logic with specific conventions by specific method names
    // Documentation link: https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries
    List<User> findAllByNameContainingAndPublicProfileTrue(String string);
    
    @Query("{'publicProfile':true}")
    List<User> findAllPublic();
    
    @Query(value="{ id : ?0}", fields="{ publicProfile : 1 }")
    Optional<User> isUserPublic(String id);

    Boolean existsByName(String name);

    
    
    
}
