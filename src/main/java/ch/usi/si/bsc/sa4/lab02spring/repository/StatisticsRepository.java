package ch.usi.si.bsc.sa4.lab02spring.repository;

import ch.usi.si.bsc.sa4.lab02spring.model.Statistics.UserStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Collection of statistics for all users.
 */
@Repository
public interface StatisticsRepository extends MongoRepository<UserStatistics,String>{

}
