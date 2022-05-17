package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.language.*;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.ConcreteTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.WaterTile;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("The Language")
public class LanguageTests {

    Context context;

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
        level = new Level("Level 1", "the first level",1, EWorld.EARTH,
                6,board,robot,allowedCommands,"../../assets/thumbnail.jpg");
        levelValidation = new LevelValidation();
        context = new Context(board, robot, 4, levelValidation);
    }

    @DisplayName("should be able to return its board")
    @Test
    public void testGetBoard() {
        var thisBoard = context.getBoard();
        assertEquals(board , thisBoard, "The board should be the same");
    }

    @DisplayName("should be able to get the robot")
    @Test
    public void testGetRobot() {
        var thisRobot = context.getRobot();
        assertEquals(robot, thisRobot, "The robot should be the same");
    }


    @DisplayName("should be able to get the level validation")
    @Test
    public void testGetLevelValidation() {
        var thisLevelValidation = context.getLevelValidation();
        assertEquals(levelValidation, thisLevelValidation, "The level validation should be the same");
    }

    @DisplayName("should be able to get the function table")
    @Test
    public void testGetFunctionTable() {
        var thisFunctionTable = context.getFunctionTable();
        assertNotNull(thisFunctionTable, "The function table should not be null");
    }

    @DisplayName("should be able to set the function table")
    @Test
    public void testSetFunctionTable() {
        Map<String, Action> thisFunctionTable = new HashMap<>();
        thisFunctionTable.put("test", new ActionMoveForward(null));
        assertNotNull(thisFunctionTable, "The function table should not be null");
        assertTrue(thisFunctionTable.containsKey("test"), "The function table should contain the key test");
        assertNotNull(thisFunctionTable.get("test"), "The function table should return the action test");
        assertFalse(thisFunctionTable.containsKey("test2"), "The function table should not contain the key test2");
    }

    @DisplayName("should be able to return if the player is dead or not")
    @Test
    public void testIsDead() {
        assertFalse(context.isDead(), "The player should not be dead");
        context.setDead(true);
        assertTrue(context.isDead(), "The player should be dead");
        context.setDead(false);
        assertFalse(context.isDead(), "The player should not be dead");
    }

    @DisplayName("should be able to get the collected coins number")
    @Test
    public void testGetCollectedCoins() {
        var thisContext = context;
        assertEquals(0, thisContext.getCollectedCoins(), "The collected coins should be 0");
        thisContext.incrementCollectedCoins();
        assertEquals(1, thisContext.getCollectedCoins(), "The collected coins should be 1");
    }

    @DisplayName("should be able to execute a program with basic commands")
    @Test
    public void testExecuteBasicProgram() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionTurnLeft(new ActionTurnRight(new ActionCollectCoin(null))))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertFalse(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "doesn't add death animation when the player is not dead");
        assertTrue(result.getAnimations().contains(EAnimation.MOVE_FORWARD), "adds move forward animation");
    }

    @DisplayName("should not be able to run a program with two main blocks")
    @Test
    public void testExecuteProgramWithTwoMainBlocks() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(null), new ActionMoveForward(null)));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to run a program without a main block")
    @Test
    public void testExecuteProgramWithoutMainBlock() {
        Program thisProgram = new Program(List.of());
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to run a program without a main block")
    @Test
    public void testExecuteProgramWithoutMainBlock2() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("pippo", new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should be able to run a program with functions")
    @Test
    public void testExecuteProgramWithFunctions() {
        Program thisProgram = new Program(List.of(new ActionFunctionCall("pippo", null),
                                                  new FunctionDefinition("pippo",
                                                                          new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertFalse(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "doesn't add death animation when the player is not dead");
        assertTrue(result.getAnimations().contains(EAnimation.MOVE_FORWARD), "adds move forward animation");
        assertFalse(result.hasErrors(), "doesn't add error when the program can be executed");
        assertTrue(context.getFunctionTable().containsKey("pippo"), "adds function to the function table");
    }

    @DisplayName("should not be able to define a function with the same name as a built-in function")
    @Test
    public void testDefineFunctionWithBuiltInFunctionName() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("moveForward", new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to define a function with the same name of a function defined in the same program")
    @Test
    public void testDefineFunctionWithSameName() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("pippo", new ActionMoveForward(null)),
                                                  new FunctionDefinition("pippo", new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not complete a level with too may commands")
    @Test
    public void testTooManyCommands() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(
                                                    new ActionCollectCoin(
                                                        new ActionMoveForward(
                                                             new ActionCollectCoin(
                                                                 new ActionTurnLeft(null)))))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should be able to complete a level")
    @Test
    public void testCompleteLevel() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionCollectCoin(new ActionMoveForward(new ActionCollectCoin(null))))));
        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(EAnimation.MOVE_FORWARD), "adds move forward animation");
        assertTrue(result.getAnimations().contains(EAnimation.JUMP), "adds jump animation");
        assertTrue(result.getAnimations().contains(EAnimation.EMOTE_DANCE),
                    "adds emote dance animation when the level is completed");
        assertEquals(2, context.getCollectedCoins(), "count collected coins");
        assertFalse(context.isDead(), "The player should not be dead");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }


}
