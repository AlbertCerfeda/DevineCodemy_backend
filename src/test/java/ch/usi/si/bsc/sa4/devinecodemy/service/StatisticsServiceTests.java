package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("The statistics service")
public class StatisticsServiceTests {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    StatisticsRepository statisticsRepository;

    @MockBean
    GamePlayer gamePlayer;

    @MockBean
    LevelValidation levelValidation;

    @BeforeEach
    void setup() {
        statisticsService.addStats("1");
        List<Tile> grid = List.of(
                new GrassTile(0, 0, 0)
        );
        List<Item> items = List.of(
                new CoinItem(4, 7)
        );
        var board = new Board(grid, items, 1);
        var robot = new Robot(0, 0, EOrientation.DOWN);
        var commands = List.of(EAction.MOVE_FORWARD);
        gamePlayer = new GamePlayer(new Level( "test name", "test description", 1, EWorld.EARTH, 10, board, robot, commands, "../assets/level10.png"));
        levelValidation = new LevelValidation();
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
        statisticsService.addStats("2",gamePlayer,levelValidation);
    }

    @DisplayName("after adding")
    @Nested
    class AfterAddingTests {

        @BeforeEach
        void setup() {
            statisticsService.addStats("2",gamePlayer,levelValidation);
        }

        @DisplayName("should not add a stat with a id and other parameters if one with the same id already exists")
        @Test
        public void testAddStatIfAlreadyExisting() {
            statisticsService.addStats("2",gamePlayer,levelValidation);
        }
    }

}
