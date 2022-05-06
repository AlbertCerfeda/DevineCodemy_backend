package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The level")
public class LevelTests {

    private Level level;

    @BeforeEach
    void setup() {
        List<Tile> grid = List.of(
                new GrassTile(0, 0, 0)
        );
        List<Item> items = List.of(
                new CoinItem(4, 7)
        );
        var board = new Board(grid, items, 1);
        var robot = new Robot(0, 0, EOrientation.DOWN);
        var commands = List.of(EAction.MOVE_FORWARD);
        level = new Level("test name", "test description", 10, 10, board, robot, commands, "");
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("has the same name provided in the constructor")
        @Test
        void testGetName() {
            var actualName = level.getName();
            var expectedName = "test name";
            assertEquals(expectedName, actualName, "name is not the one provided in the constructor");
        }

        @DisplayName("has the same description provided in the constructor")
        @Test
        void testGetDescription() {
            var actualDescription = level.getDescription();
            var expectedDescription = "test description";
            assertEquals(expectedDescription, actualDescription, "description is not the one provided in the constructor");
        }

        @DisplayName("has the same maxSteps provided in the constructor")
        @Test
        void testGetMaxSteps() {
            var actualMaxSteps = level.getMaxCommandsNumber();
            var expectedMaxSteps = 10;
            assertEquals(expectedMaxSteps, actualMaxSteps, "maxSteps is not the one provided in the constructor");
        }

        @DisplayName("has the same board provided in the constructor")
        @Test
        void testGetBoard() {
            var actualBoard = level.getBoard();
            List<Tile> grid = List.of(
                    new GrassTile(0, 0, 0)
            );
            List<Item> items = List.of(
                    new CoinItem(4, 7)
            );
            var expectedBoard = new Board(grid, items, 1);
            assertEquals(expectedBoard, actualBoard, "board is not the one provided in the constructor");
        }

        @DisplayName("has the same robot provided in the constructor")
        @Test
        void testGetRobot() {
            var actualRobot = level.getRobot();
            var expectedRobot = new Robot(0, 0, EOrientation.DOWN);
            assertEquals(expectedRobot, actualRobot, "robot is not the one provided in the constructor");
        }

        @DisplayName("has the same maxSteps provided in the constructor")
        @Test
        void testGetAllowedCommands() {
            var actualAllowedCommands = level.getAllowedCommands();
            var expectedAllowedCommands = List.of(EAction.MOVE_FORWARD);
            assertEquals(expectedAllowedCommands, actualAllowedCommands, "allowedCommands is not the one provided in the constructor");
        }

    }

}
