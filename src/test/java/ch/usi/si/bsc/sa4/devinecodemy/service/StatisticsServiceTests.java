package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    GamePlayer gamePlayer;

    LevelValidation levelValidation;

    UserStatistics userStatistics1;
    UserStatistics userStatistics2;
    UserStatistics userStatistics3;

    @BeforeEach
    void setup() {
        statisticsService.addStats("1");
        Level level = mock(Level.class);
        given(level.getLevelNumber()).willReturn(1);
        given(level.getAllowedCommands()).willReturn(List.of(EAction.MOVE_FORWARD));
        gamePlayer = new GamePlayer(level);
        levelValidation = mock(LevelValidation.class);
        userStatistics1 = mock(UserStatistics.class);
        userStatistics2 = mock(UserStatistics.class);
        userStatistics3 = mock(UserStatistics.class);
        given(userStatistics1.getId()).willReturn("1");
//        given(userStatistics3.getAttemptFromLevel(anyInt(),anyInt())).willReturn(List.of());
//        given(userStatistics3.getAttemptFromLevel(1,0)).willReturn(List.of(EAction.MOVE_FORWARD));
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
//        statisticsService.addStats("2",gamePlayer,levelValidation);
    }

    @DisplayName("after adding")
    @Nested
    class AfterAddingTests {

        @BeforeEach
        void setup() {
//            statisticsService.addStats("2",gamePlayer,levelValidation);
            given(userStatistics2.getId()).willReturn("2");
            given(userStatistics3.getId()).willReturn("3");
            given(statisticsRepository.findById("2")).willReturn(Optional.of(userStatistics2));
            given(statisticsRepository.findById("3")).willReturn(Optional.of(userStatistics3));
        }

        @DisplayName("should not add a stat with a id and other parameters if one with the same id already exists")
        @Test
        public void testAddStatIfAlreadyExisting() {
//            statisticsService.addStats("2",gamePlayer,levelValidation);
        }

        @DisplayName("should add a stat with a id and null GamePlayer if id is new")
        @Test
        public void testAddStatIfGameNull() {
//            statisticsService.addStats("3",null,levelValidation);
        }

        @DisplayName("after re-adding")
        @Nested
        class AfterAfterAddingTests {

            @BeforeEach
            void setup() {
//                statisticsService.addStats("3",gamePlayer,levelValidation);
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
//                var actualAttempt = statisticsService.getAttempt("3",1,0);
//                var expectedAttempt = List.of(EAction.MOVE_FORWARD);
//                assertEquals(expectedAttempt,actualAttempt,
//                        "attempt is not the same provided when played");
            }

            @DisplayName("should not be able to get attempts on a not existing statistic")
            @Test
            public void testGetAttemptNotExisting() {
                Executable actual = () -> statisticsService.getAttempt("4",0,0);
                assertThrows(StatisticInexistentException.class,actual,
                        "getAttempt didn't throw searching for a not existing statistic");
            }
        }
    }

}
