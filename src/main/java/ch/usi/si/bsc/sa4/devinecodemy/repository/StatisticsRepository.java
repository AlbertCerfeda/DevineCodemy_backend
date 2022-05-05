package ch.usi.si.bsc.sa4.devinecodemy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;

/**
 * Collection of statistics for all users.
 */
@Repository
public interface StatisticsRepository extends MongoRepository<UserStatistics,String>{

}
