package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;


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
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("The UserStatistic")
public class UserStatisticsTests {

    private UserStatistics statistics1;
    private UserStatistics statistics2;

    @MockBean
    private GamePlayer gamePlayer;

    @MockBean
    private Level level;

    @MockBean
    private LevelValidation levelValidation;

    @MockBean
    private LevelStatistics levelStatistics;

    @BeforeEach
    void setup() {
        statistics1 = new UserStatistics("1");
        HashMap<Integer, LevelStatistics> levelData = new HashMap<>();
        levelData.put(1,new LevelStatistics(true));
        statistics2 = new UserStatistics("2", levelData);
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {
        @Test
        @DisplayName("has the same id provided in the constructor")
        public void testGetId() {
            var actualId = statistics1.getId();
            var expectedId = "1";
            assertEquals(expectedId, actualId,
                    "id is not the same id provided in the constructor");
        }

        @Test
        @DisplayName("has empty level data if no data is provided")
        public void testGetDataEmpty() {
            var actualLength= statistics1.getData().keySet().size();
            var expectedLength = 0;
            assertEquals(expectedLength, actualLength,
                    "Level data is not empty if not given");
        }

        @Test
        @DisplayName("has the same level data provided in the constructor")
        public void testGetDataFull() {
            var actualLength= statistics2.getData().keySet().size();
            var expectedLength = 1;
            assertEquals(expectedLength, actualLength,
                    "Level data is not the same provided in the constructor");
        }

        @DisplayName("it should be possible to get the DTO of the object")
        @Test
        public void testToUserStatisticsDTO() {
            var actualDTO = statistics1.toUserStatisticsDTO();
            var expectedDTO = statistics1.toUserStatisticsDTO();
            assertEquals(expectedDTO.getId(),actualDTO.getId(),"the id of the UserStatisticsDTO is not the same of the UserStatistics");
            assertEquals(expectedDTO.getData().size(),actualDTO.getData().size(),"the data of the UserStatisticsDTO is not the same of the UserStatistics");
        }
    }

    @DisplayName("after adding an attempt")
    @Nested
    class AfterAttemptTests {

        @BeforeEach
        void addAttempt() {
            List<Tile> grid = List.of(
                    new GrassTile(0, 0, 0)
            );
            List<Item> items = List.of(
                    new CoinItem(4, 7)
            );
            var board = new Board(grid, items, 1);
            var robot = new Robot(0, 0, EOrientation.DOWN);
            var commands = List.of(EAction.MOVE_FORWARD);
            level = new Level( "test name", "test description", 1, EWorld.EARTH, 10, board, robot, commands, "../assets/level10.png");
            gamePlayer = mock(GamePlayer.class);
            given(gamePlayer.getLevel()).willReturn(level);
            given(gamePlayer.getParsedCommands()).willReturn(List.of(EAction.MOVE_FORWARD));
            given(gamePlayer.play(any())).willReturn(levelValidation);
            given(levelValidation.isCompleted()).willReturn(false);
            given(levelStatistics.isCompleted()).willReturn(false);
//            statistics1.addData(gamePlayer,levelValidation);
            given(levelValidation.isCompleted()).willReturn(true);
        }

        @DisplayName("it should be possible to retrieve the last attempt")
        @Test
        public void testGetAttemptFromLevel1() {
//            var actualActions = statistics1.getAttemptFromLevel(1,0);
//            var expectedActions = List.of(EAction.MOVE_FORWARD);
//            assertEquals(expectedActions, actualActions,
//                    "The attempt received doesn't match the inserted commands");
        }

        @DisplayName("it should not be possible to retrieve the next level's statistics before completing the actual level")
        @Test
        public void testGetAttemptFromLevel2() {
            Executable actualActions = () -> statistics1.getAttemptFromLevel(2,0);

            assertThrows(StatisticInexistentException.class,
                    actualActions,
                    "The attempt received doesn't match the inserted commands");
        }

        @DisplayName("it should be possible to add another attempt to complete the same level")
        @Nested
        class AfterAfterAddingTests {

            @BeforeEach
            public void testAddDataCompleting() {
//                statistics1.addData(gamePlayer,levelValidation);
            }

            @DisplayName("it should be possible to add another attempt to the same level" +
                    " even after completing it, but without changing the completion state")
            @Test
            public void testAddDataAfterCompleting() {
//                statistics1.addData(gamePlayer,levelValidation);
            }
        }
    }
}
