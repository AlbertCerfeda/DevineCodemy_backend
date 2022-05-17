package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("The level")
public class LevelTests {

    private Level level;
    private List<Tile> grid;
    private List<Item> items;

    @BeforeEach
    void setup() {
        grid = List.of(
                new GrassTile(0, 0, 0)
        );
        items = List.of(
                new CoinItem(4, 7)
        );
        var board = new Board(grid, items, 1);
        var robot = new Robot(0, 0, EOrientation.DOWN);
        var commands = List.of(EAction.MOVE_FORWARD);
        level = new Level( "test name", "test description", 10, EWorld.SKY, 10, board, robot, commands, "../assets/level10.png");
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

        @DisplayName("has the same levelNumber provided in the constructor")
        @Test
        void testGetLevelNumber() {
            var actualLevelNumber = level.getLevelNumber();
            var expectedLevelNumber = 10;
            assertEquals(expectedLevelNumber, actualLevelNumber, "levelNumber is not the one provided in the constructor");
        }

        @DisplayName("has the same thumbnailSrc provided in the constructor")
        @Test
        void testGetThumbnailSrc() {
            var actualThumbnailSrc = level.getThumbnailSrc();
            var expectedThumbnailSrc = "../assets/level10.png";
            assertEquals(expectedThumbnailSrc, actualThumbnailSrc, "thumbnailSrc is not the one provided in the constructor");
        }

        @DisplayName("can return the correct levelDTO")
        @Test
        void testToLevelDTO() {
            var actualLevelDTO = level.toLevelDTO();
            assertEquals(level.getName(), actualLevelDTO.getName(),
                    "levelDTO does not have the same name as its level");
            assertEquals(level.getLevelNumber(), actualLevelDTO.getLevelNumber(),
                    "levelDTO does not have the same levelNumber as its level");
            assertEquals(level.getDescription(), actualLevelDTO.getDescription(),
                    "levelDTO does not have the same description as its level");
            assertEquals(level.getThumbnailSrc(), actualLevelDTO.getThumbnailSrc(),
                    "levelDTO does not have the same thumbnailSrc as its level");
            assertEquals(level.getMaxCommandsNumber(), actualLevelDTO.getMaxCommandsNumber(),
                    "levelDTO does not have the same maxCommandsNumber as its level");
            assertNotNull(actualLevelDTO.getAllowedCommands(), "levelDTO allowedCommands is null");
            assertNotNull(actualLevelDTO.getBoard(), "levelDTO board is null");
            var actualTileDTO = actualLevelDTO.getBoard().getGrid().get(0);
            var expectedTile = grid.get(0);
            assertEquals(expectedTile.getPosX(),actualTileDTO.getPosX(),
                    "tileDTO does not have the same posX as its tile");
            assertEquals(expectedTile.getPosY(),actualTileDTO.getPosY(),
                    "tileDTO does not have the same posY as its tile");
            assertEquals(expectedTile.getPosZ(),actualTileDTO.getPosZ(),
                    "tileDTO does not have the same posZ as its tile");
            assertEquals(expectedTile.getType().name(),actualTileDTO.getType(),
                    "tileDTO does not have the same type as its tile");
            var actualItemDTO = actualLevelDTO.getBoard().getItems().get(0);
            var expectedItem = items.get(0);
            assertEquals(expectedItem.getPosX(),actualItemDTO.getPosX(),
                    "itemDTO does not have the same posX as its item");
            assertEquals(expectedItem.getPosY(),actualItemDTO.getPosY(),
                    "itemDTO does not have the same posY as its item");
            assertEquals(expectedItem.getType().name(),actualItemDTO.getType(),
                    "itemDTO does not have the same posY as its item");
            assertNotNull(actualLevelDTO.getRobot(), "levelDTO robot is null");
            assertEquals(level.getLevelWorld().getDisplayName(), actualLevelDTO.getLevelWorld(),
                    "levelDTO does not have the same EWorld as its level");
        }

        @DisplayName("can return the correct levelDTO with info only")
        @Test
        void testToLevelInfoDTO() {
            var actualLevelDTO = level.toLevelInfoDTO();
            assertEquals(level.getName(), actualLevelDTO.getName(), "levelDTO does not have the same name as its level");
            assertEquals(level.getLevelNumber(), actualLevelDTO.getLevelNumber(), "levelDTO does not have the same levelNumber as its level");
            assertEquals(level.getDescription(), actualLevelDTO.getDescription(), "levelDTO does not have the same description as its level");
            assertEquals(level.getThumbnailSrc(), actualLevelDTO.getThumbnailSrc(), "levelDTO does not have the same thumbnailSrc as its level");
            assertEquals(level.getMaxCommandsNumber(), actualLevelDTO.getMaxCommandsNumber(), "levelDTO does not have the same maxCommandsNumber as its level");
            assertNotNull(actualLevelDTO.getAllowedCommands(), "levelDTO allowedCommands is null");
            assertNull(actualLevelDTO.getBoard(), "levelDTO board is not null");
            assertNull(actualLevelDTO.getRobot(), "levelDTO robot is not null");
        }

    }

}
