package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.ConcreteTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.WaterTile;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("The Game player")
public class GamePlayerTests {

    GamePlayer gamePlayer;

    Board board;
    Robot robot;
    Level level;
    LevelValidation levelValidation;

    @MockBean
    StatisticsRepository statisticsRepository;

    @BeforeEach
    void setup() {
        robot = new Robot(0,0,EOrientation.DOWN);
        List<Tile> grid = List.of(
                new GrassTile(0,0,0),
                new GrassTile(0,1,0),
                new ConcreteTile(0,2,0),
                new GrassTile(1,0,0),
                new GrassTile(1,1,0),
                new GrassTile(1,2,0),
                new WaterTile(2,0,0),
                new WaterTile(2,1,0),
                new WaterTile(2,2,0)
        );
        List<Item> items = List.of(new CoinItem(0,2),
                new CoinItem(0,1));
        board = new Board(grid,items,2);
        List<EAction> allowedCommands = List.of(EAction.MOVE_FORWARD,EAction.COLLECT_COIN,EAction.TURN_LEFT);
        level = new Level("Level 1", "the first level",1, EWorld.PURGATORY,
                6,board,robot,allowedCommands,"../../assets/thumbnail.jpg");
        gamePlayer = new GamePlayer(level);
        levelValidation = new LevelValidation();
    }

    @DisplayName("should be able to return its level")
    @Test
    public void testGetLevel() {
        var actualLevel = gamePlayer.getLevel();
        var expectedLevel = level;
        assertEquals(expectedLevel.getLevelNumber(),actualLevel.getLevelNumber(),
                "number is not the one provided on creation");
        assertEquals(expectedLevel.getAllowedCommands(),actualLevel.getAllowedCommands(),
                "commands are not the one provided on creation");
    }

    @DisplayName("should be able to get the parsed Commands")
    @Test
    public void testGetParsedCommands() {
        var actualCommands = gamePlayer.getParsedCommands();
        var expectedCommands = List.of();
        assertEquals(expectedCommands,actualCommands,
                "number is not the one provided on creation");
    }

    @DisplayName("should be able to play a list of commands to complete the Level")
    @Test
    public void testPlay() {
        var actualValidation = gamePlayer.play(List.of("moveForward"));
        assertFalse(actualValidation.isCompleted(),
                "the completion is not the one expected");
    }

    @DisplayName("should be able to parse a list of strings into commands")
    @Test
    public void testParseCommands() {
        var actualValidation = gamePlayer.play(List.of("moveForward","turnLeft"));
        assertFalse(actualValidation.isCompleted(),
                "the completion is not the one expected");
    }

    @DisplayName("should be able to parse a list of strings into commands")
    @Test
    public void testParseCommandsWrongCommands() {
        LevelValidation actualValidation = gamePlayer.play(List.of("moveForwar","turnRight"));
        List<String> actualValidationErrors = actualValidation.getErrors();
        var expectedValidationError1 = "Unknown command: 'moveForwar'";
        assertEquals(expectedValidationError1,actualValidationErrors.get(0),
                "doesn't add error when inserting commands with typos");
        var expectedValidationError2 = "Command not allowed: 'turnRight'";
        assertEquals(expectedValidationError2,actualValidationErrors.get(1),
                "doesn't add error when inserting not allowed commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "doesn't add death animation when inserting not allowed commands");
    }

    @DisplayName("should have not completed the level when inserting more commands than allowed")
    @Test
    public void testParseCommandsTooManyCommands() {
        List<String> actualValidationErrors = gamePlayer.play(
                List.of("moveForward",
                        "moveForward",
                        "moveForward",
                        "turnLeft",
                        "turnLeft",
                        "turnLeft",
                        "turnLeft",
                        "turnLeft")).getErrors();
        var expectedValidationError = "Too many commands: you can use only " + level.getMaxCommandsNumber() + " commands.";
        assertEquals(expectedValidationError,actualValidationErrors.get(0),
                "doesn't add error when inserting too many commands");
    }

    @DisplayName("should not complete the level when inserting commands to go out of the board")
    @Test
    public void testPlayWrongOrientationMoveForwardCommands() {
        LevelValidation actualValidation = gamePlayer.play(
                List.of("moveForward",
                        "moveForward",
                        "moveForward",
                        "moveForward",
                        "turnLeft"));
        assertFalse(actualValidation.isCompleted(),
                "completes when inserting wrong moveForward commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "doesn't add death animation when inserting wrong moveForward commands");
    }

    @DisplayName("should collect the coin when inserting the collectCoin command on a tile with an item")
    @Test
    public void testPlayCollectRight() {
        LevelValidation actualValidation = gamePlayer.play(
                List.of("moveForward",
                        "collectCoin",
                        "moveForward",
                        "turnLeft"));
        assertFalse(actualValidation.isCompleted(),
                "completes the level when inserting wrong commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.MOVE_FORWARD),
                "doesn't add moveForward animation when inserting the moveForward command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.JUMP),
                "doesn't add jump animation when inserting the collectCoin command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.TURN_LEFT),
                "doesn't add turnLeft animation when inserting the turnLeft command");
    }

    @DisplayName("should not collect the coin when inserting the collectCoin command on a tile without an item")
    @Test
    public void testPlayCollectWrong() {
        LevelValidation actualValidation = gamePlayer.play(
                List.of("moveForward",
                        "turnLeft",
                        "moveForward",
                        "collectCoin",
                        "moveForward"));
        assertFalse(actualValidation.isCompleted(),
                "completes the level when inserting wrong commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.MOVE_FORWARD),
                "doesn't add moveForward animation when inserting the moveForward command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.JUMP),
                "doesn't add jump animation when inserting the collectCoin command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.EMOTE_NO),
                "doesn't add emote-no animation when inserting the collectCoin command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.TURN_LEFT),
                "doesn't add turnLeft animation when inserting the turnLeft command");
    }

    @DisplayName("should be able to complete the level inserting a valid list of commands below" +
            " the max limit of the specific level")
    @Test
    public void testPlayRightCommands() {
        LevelValidation actualValidation = gamePlayer.play(
                List.of("moveForward",
                        "collectCoin",
                        "moveForward",
                        "collectCoin"));
        assertTrue(actualValidation.isCompleted(),
                "completes the level when inserting wrong commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.MOVE_FORWARD),
                "doesn't add moveForward animation when inserting the moveForward command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.JUMP),
                "doesn't add jump animation when inserting the collectCoin command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.EMOTE_DANCE),
                "doesn't add dance animation when inserting right commands");
    }

    @DisplayName("should be able to play any command if the level allows it")
    @Test
    public void testPlayUnknownCommand() {
        List<EAction> allowedCommands = List.of(
                EAction.MOVE_FORWARD,
                EAction.COLLECT_COIN,
                EAction.TURN_LEFT,
                EAction.TURN_RIGHT);
        level = new Level("Level 1", "the first level",1, EWorld.PURGATORY,
                6,board,robot,allowedCommands,"../../assets/thumbnail.jpg");
        gamePlayer = new GamePlayer(level);
        LevelValidation actualValidation = gamePlayer.play(
                List.of("moveForward",
                        "collectCoin",
                        "turnLeft",
                        "turnRight"));
        assertFalse(actualValidation.isCompleted(),
                "completes the level when inserting wrong commands");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.MOVE_FORWARD),
                "doesn't add moveForward animation when inserting the moveForward command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.JUMP),
                "doesn't add jump animation when inserting the collectCoin command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.TURN_LEFT),
                "doesn't add turnLeft animation when inserting the turnLeft command");
        assertTrue(actualValidation.getAnimations().contains(EAnimation.TURN_RIGHT),
                "doesn't add turnRight animation when inserting the turnRight command");
    }
}
