package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("The LevelStatistics")
public class LevelStatisticsTests {

    private LevelStatistics levelStatistics1;

    @MockBean
    private GamePlayer gamePlayer;

    @BeforeEach
    void setup() {
        levelStatistics1 = new LevelStatistics(false);
        gamePlayer = mock(GamePlayer.class);
        given(gamePlayer.getParsedCommands()).willReturn(List.of(EAction.MOVE_FORWARD));
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("it should be possible to check if the level is completed")
        @Test
        public void testIsCompleted() {
            var actualCompletion = levelStatistics1.isCompleted();
            var expectedCompletion = false;
            assertEquals(expectedCompletion,actualCompletion,
                    "completed is not the same as in the constructor");
        }

        @DisplayName("it should be possible to get an empty list of attempts")
        @Test
        public void testGetAllAttempts() {
            var actualSize = levelStatistics1.getAllAttempts().size();
            var expectedSize = 0;
            assertEquals(expectedSize,actualSize, "data is not empty");
        }

        @DisplayName("it should not be possible to get a not played attempts")
        @Test
        public void testGetAttemptEmpty() {
            Executable actual = () -> levelStatistics1.getAttempt(0);

            assertThrows(StatisticInexistentException.class, actual,
                    "asking for a not played attempt should throw an exception");
        }

        @DisplayName("it should be possible to set a list of attempts")
        @Test
        public void testSetData() {
            List<List<EAction>> data = List.of(List.of(EAction.MOVE_FORWARD));
//            levelStatistics1.setData(data);
        }

        @DisplayName("it should be possible to set a new completed status")
        @Test
        public void testSetCompleted() {
            boolean completed = true;
            levelStatistics1.setCompleted(completed);
        }

        @DisplayName("it should be possible to add a new attempt")
        @Test
        public void testAdd() {
//            given(gamePlayer.getParsedCommands()).willReturn(List.of());
//            levelStatistics1.add(gamePlayer);
        }
    }

    @DisplayName("after an attempt")
    @Nested
    class AfterAddingTests {

        @BeforeEach
        public void setup() {
//            given(gamePlayer.getParsedCommands()).willReturn(List.of());
//            levelStatistics1.add(gamePlayer);
        }

        @DisplayName("it should be possible to retrieve it")
        @Test
        public void testGetAttemptFullCorrect() {
//            var actualAttempt = levelStatistics1.getAttempt(0);
//            var expectedAttempt = List.of();
//            assertEquals(expectedAttempt,actualAttempt,
//                    "the attempt is not the one played");
        }

        @DisplayName("it should be possible to retrieve the size of the data")
        @Test
        public void testGetAttemptFullSpecial() {
//            var actualAttempt = levelStatistics1.getAttempt(-1);
//            var expectedAttempt = List.of();
//            assertEquals(expectedAttempt,actualAttempt,
//                    "the attempt is not the one played");
        }
    }
}
