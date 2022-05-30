package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.LevelStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import ch.usi.si.bsc.sa4.devinecodemy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("The statistics service")
public class StatisticsServiceTests {

    @Autowired
    StatisticsService statisticsService;

    @MockBean
    StatisticsRepository statisticsRepository;

    @MockBean
    UserRepository userRepository;

    LevelValidation levelValidation;

    User user1;
    User user2;
    User user3;
    User user4;
    UserStatistics userStatistics1;
    UserStatistics userStatistics2;
    UserStatistics userStatistics3;
    String attemptNull;
    String attempt;

    @BeforeEach
    void setup() {
        user1 = new User("1","name","username","email","avatar 1","bio",
                new SocialMedia("twitter","skype","linkedin"));
        user2 = new User("2","name","username","email","avatar 2","bio",
                new SocialMedia("twitter","skype","linkedin"));
        user3 = new User("3","name","username","email","avatar 3","bio",
                new SocialMedia("twitter","skype","linkedin"));
        user4 = new User("4","name","username","email","avatar 4","bio",
                new SocialMedia("twitter","skype","linkedin"));
        statisticsService.addStats("1");
        Level level = mock(Level.class);
        given(level.getLevelNumber()).willReturn(1);
        given(level.getAllowedCommands()).willReturn(List.of(ECategory.BASIC_COMMANDS));
        levelValidation = mock(LevelValidation.class);
        userStatistics1 = mock(UserStatistics.class);
        userStatistics2 = mock(UserStatistics.class);
        userStatistics3 = mock(UserStatistics.class);
        attempt = "{}";
        given(userStatistics1.getId()).willReturn("1");
        given(userStatistics3.getAttemptFromLevel(anyInt(),anyInt())).willReturn(attemptNull);
        given(userStatistics3.getAttemptFromLevel(1,0)).willReturn(attempt);
        given(statisticsRepository.findById("1")).willReturn(Optional.of(userStatistics1));
        given(statisticsRepository.findAll()).willReturn(List.of(userStatistics1,userStatistics2,userStatistics3));
    }

    @DisplayName("should be able to find a specific statistic")
    @Test
    public void testGetById() {
        var actualId = statisticsService.getById("1").get().getId();
        var expectedId = "1";
        assertEquals(expectedId, actualId,
                "id should be the same as the one of the search by id");
    }

    @DisplayName("should not be able to find a not added statistic")
    @Test
    public void testGetByIdEmtpy() {
        var actualUser = statisticsService.getById("2");
        var expectedUser = Optional.empty();
        assertEquals(expectedUser, actualUser,
                "should receive empty if searching for a not existing statistic");
    }

    @DisplayName("should not add a stat with the same id if one already existing")
    @Test
    public void testAddStatOnlyIdIfAlreadyExisting() {
        statisticsService.addStats("1");
    }

    @DisplayName("should add a stat with a new id and other parameters")
    @Test
    public void testAddStatIfNotAlreadyExisting() {
        statisticsService.addStats("2",1,attempt,false);
    }

    @DisplayName("after adding")
    @Nested
    class AfterAddingTests {

        @BeforeEach
        void setup() {
            statisticsService.addStats("2",1,attempt,false);
            given(userStatistics2.getId()).willReturn("2");
            given(userStatistics3.getId()).willReturn("3");
            given(statisticsRepository.findById("2")).willReturn(Optional.of(userStatistics2));
            given(statisticsRepository.findById("3")).willReturn(Optional.of(userStatistics3));
        }

        @DisplayName("should not add a stat with a id and other parameters if one with the same id already exists")
        @Test
        public void testAddStatIfAlreadyExisting() {
            statisticsService.addStats("2",1,attemptNull,false);
        }

        @DisplayName("after re-adding")
        @Nested
        class AfterAfterAddingTests {

            @BeforeEach
            void setup() {
                statisticsService.addStats("3",1,attempt,false);
            }

            @DisplayName("should be able to get all the added statistics")
            @Test
            public void testGetAll() {
                var actualValues = statisticsService.getAll();
                var expectedValue1 = "1";
                var expectedValue2 = "2";
                var expectedValue3 = "3";
                String ERROR_MESSAGE = "Added statistics should be retrievable";
                assertEquals(actualValues.get(0).getId(),expectedValue1, ERROR_MESSAGE);
                assertEquals(actualValues.get(1).getId(),expectedValue2, ERROR_MESSAGE);
                assertEquals(actualValues.get(2).getId(),expectedValue3, ERROR_MESSAGE);
            }

            @DisplayName("should be able to get added attempt")
            @Test
            public void testGetAttemptExisting() {
                var actualAttempt = statisticsService.getAttempt("3",1,0);
                var expectedAttempt = attempt;
                assertEquals(expectedAttempt,actualAttempt,
                        "attempt is not the same provided when played");
            }

            @DisplayName("should not be able to get attempts on a not existing statistic")
            @Test
            public void testGetAttemptNotExisting() {
                Executable actual = () -> statisticsService.getAttempt("4",0,0);
                assertThrows(StatisticInexistentException.class,actual,
                        "getAttempt didn't throw searching for a not existing statistic");
            }

            @DisplayName("should be able to get the number of completed levels")
            @Test
            public void testGetCompletedLevels() {
                var data = new HashMap<Integer, LevelStatistics>();
                given(userStatistics1.getData()).willReturn(data);
                var result = statisticsService.getCompletedLevels(user1);
                assertEquals(data.keySet().size(),result,
                        "the number of completed levels does not match the data of the user");
                given(statisticsRepository.findById("4")).willReturn(Optional.empty());
            }

            @DisplayName("should get 0 when getting the number of completed levels of a not existing statistic")
            @Test
            public void testGetCompletedLevelsOfNotExistingStats() {
                given(statisticsRepository.findById("4")).willReturn(Optional.empty());
                var result = statisticsService.getCompletedLevels(user4);
                assertEquals(0,result,
                        "the number of completed levels should be 0 when asking for a not existing statistics");
            }

            @DisplayName("should be able to get all the LeaderBoard dto version of the users")
            @Test
            public void testGetLeaderboardUsers() {
                var data = new HashMap<Integer, LevelStatistics>();
                given(userStatistics1.getData()).willReturn(data);
                given(userStatistics2.getData()).willReturn(data);
                var data3 = new HashMap<Integer,LevelStatistics>();
                data3.put(1,new LevelStatistics(true));
                data3.put(2,new LevelStatistics(true));
                given(userStatistics3.getData()).willReturn(data3);
                given(statisticsRepository.findById("4")).willReturn(Optional.empty());
                given(userRepository.findAll()).willReturn(List.of(user1,user2,user3,user4));
                var result = statisticsService.getLeaderboardUsers();
                assertEquals(4,result.size(),
                        "should get the same number of users as the ones in the repo");
                assertEquals(user1.getAvatarUrl(),result.get(0).getAvatarUrl(),
                        "the avatar of the first user dto does not match the avatar of the user");
                assertEquals(0,result.get(0).getCompletedLevels(),
                        "the number of the completed levels of the first user dto" +
                                " does not match the avatar of the user");
                assertEquals(user2.getAvatarUrl(),result.get(1).getAvatarUrl(),
                        "the avatar of the second user dto does not match the avatar of the user");
                assertEquals(0,result.get(1).getCompletedLevels(),
                        "the number of the completed levels of the second user dto" +
                                " does not match the avatar of the user");
                assertEquals(user3.getAvatarUrl(),result.get(2).getAvatarUrl(),
                        "the avatar of the third user dto does not match the avatar of the user");
                assertEquals(2,result.get(2).getCompletedLevels(),
                        "the number of the completed levels of the third user dto" +
                                " does not match the avatar of the user");
                assertEquals(user4.getAvatarUrl(),result.get(3).getAvatarUrl(),
                        "the avatar of the fourth user dto does not match the avatar of the user");
                assertEquals(0,result.get(3).getCompletedLevels(),
                        "the number of the completed levels of the fourth user dto" +
                                " does not match the avatar of the user");
            }

            @DisplayName("should be able to get all the LeaderBoard dto version of the users sorted by number of completed levels")
            @Test
            public void testSortedLeaderboardUsers() {
                var data = new HashMap<Integer, LevelStatistics>();
                given(userStatistics1.getData()).willReturn(data);
                given(userStatistics2.getData()).willReturn(data);
                var data3 = new HashMap<Integer,LevelStatistics>();
                data3.put(1,new LevelStatistics(true));
                data3.put(2,new LevelStatistics(true));
                given(userStatistics3.getData()).willReturn(data3);
                given(statisticsRepository.findById("4")).willReturn(Optional.empty());
                given(userRepository.findAll()).willReturn(List.of(user3,user1,user2,user4));
                var result = statisticsService.sortedLeaderboardUsers();
                assertEquals(4,result.size(),
                        "should get the same number of users as the ones in the repo");
                assertEquals(user3.getAvatarUrl(),result.get(0).getAvatarUrl(),
                        "the avatar of the first user dto does not match the avatar of the user");
                assertEquals(2,result.get(0).getCompletedLevels(),
                        "the number of the completed levels of the first user dto" +
                                " does not match the avatar of the user");
                assertEquals(user1.getAvatarUrl(),result.get(1).getAvatarUrl(),
                        "the avatar of the second user dto does not match the avatar of the user");
                assertEquals(0,result.get(1).getCompletedLevels(),
                        "the number of the completed levels of the second user dto" +
                                " does not match the avatar of the user");
                assertEquals(user2.getAvatarUrl(),result.get(2).getAvatarUrl(),
                        "the avatar of the third user dto does not match the avatar of the user");
                assertEquals(0,result.get(2).getCompletedLevels(),
                        "the number of the completed levels of the third user dto" +
                                " does not match the avatar of the user");
                assertEquals(user4.getAvatarUrl(),result.get(3).getAvatarUrl(),
                        "the avatar of the fourth user dto does not match the avatar of the user");
                assertEquals(0,result.get(3).getCompletedLevels(),
                        "the number of the completed levels of the fourth user dto" +
                                " does not match the avatar of the user");
            }
        }
    }

}
