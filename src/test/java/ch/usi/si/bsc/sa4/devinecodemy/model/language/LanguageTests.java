package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ECoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.*;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
                new WaterTile(2,2,0),
                new TeleportTile(3,0,0, false, 3, 1, 0, 1),
                new TeleportTile(3,1,0, false, 3, 0, 0, 1)
        );
        List<Item> items = List.of(new CoinItem(0,2),
                new CoinItem(0,1));
        board = new Board(grid,items,2);
        List<ECategory> allowedCommands = List.of(ECategory.BASIC_COMMANDS, ECategory.CONDITIONS, ECategory.LOGIC);
        level = new Level("Level 1", "the first level",1, EWorld.PURGATORY,
                4,board,robot,allowedCommands,"../../assets/thumbnail.jpg");
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
        assertFalse(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "doesn't add death animation when the player is not dead");
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD), "adds move forward animation");
    }

    @DisplayName("should not be able to run a program with two main blocks")
    @Test
    public void testExecuteProgramWithTwoMainBlocks() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(null), new ActionMoveForward(null)));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to run a program without a main block")
    @Test
    public void testExecuteProgramWithoutMainBlock() {
        Program thisProgram = new Program(List.of());
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to run a program without a main block")
    @Test
    public void testExecuteProgramWithoutMainBlock2() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("pippo", new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
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
        assertFalse(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "doesn't add death animation when the player is not dead");
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD), "adds move forward animation");
        assertFalse(result.hasErrors(), "doesn't add error when the program can be executed");
        assertTrue(context.getFunctionTable().containsKey("pippo"), "adds function to the function table");
    }

    @DisplayName("should not be able to define a function with the same name as a built-in function")
    @Test
    public void testDefineFunctionWithBuiltInFunctionName() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("moveForward", new ActionMoveForward(null))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
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
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not be able to complete a level with an unknown function")
    @Test
    public void testUnknownFunction() {
        Program thisProgram = new Program(List.of(new ActionFunctionCall("pippo", null)));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }


    @DisplayName("should not complete a level with too many commands")
    @Test
    public void testTooManyCommands() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(
                                                    new ActionCollectCoin(
                                                        new ActionMoveForward(
                                                             new ActionCollectCoin(
                                                                 new ActionTurnLeft(null)))))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should not complete a level with too many commands")
    @Test
    public void testTooManyCommands2() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionFunctionCall("pippo", null)),
                new FunctionDefinition("pippo",new ActionMoveForward(new ActionMoveForward(new ActionMoveForward(null))))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should not be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH),
                "adds death animation when the program cannot be executed");
        assertTrue(result.hasErrors(), "adds error when the program cannot be executed");
    }

    @DisplayName("should be able to complete a level")
    @Test
    public void testCompleteLevel() {
        Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionCollectCoin(new ActionMoveForward(new ActionCollectCoin(null))))));
        LevelValidation result = thisProgram.execute(context);

        assertTrue(this.context.getBoard().getTeleportAt(3, 0).isActive());
        assertTrue(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD), "adds move forward animation");
        assertTrue(result.getAnimations().contains(ERobotAnimation.JUMP), "adds jump animation");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DANCE),
                    "adds emote dance animation when the level is completed");
        assertEquals(2, context.getCollectedCoins(), "count collected coins");
        assertFalse(context.isDead(), "The player should not be dead");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test collect coin action")
    @Test
    public void testCollectCoin() {
        Program thisProgram = new Program(List.of(new ActionCollectCoin(new ActionMoveForward(new ActionCollectCoin(null)))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.JUMP), "adds jump animation");
        assertEquals(1, context.getCollectedCoins(), "count collected coins");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test death")
    @Test
    public void testDeath() {
        Program thisProgram = new Program(List.of(new ActionTurnRight(
                                                    new ActionMoveForward(
                                                            new ActionTurnLeft(
                                                                    new ActionTurnRight(null))))));
        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH), "adds death animation");
        assertTrue(context.isDead(), "The player should be dead");
        assertFalse(result.hasErrors(), "The level validation should not have errors");

        Program thisProgram2 = new Program(List.of(new ActionTurnRight(
                                                    new ActionMoveForward(
                                                        new ActionMoveForward(
                                                            new ActionCollectCoin(null))))));
        result = thisProgram2.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_DEATH), "adds death animation");
        assertTrue(context.isDead(), "The player should be dead");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test if statements with false")
    @Test
    public void testIfStatementsFalse() {
        Program thisProgram = new Program(List.of(new ActionIf(
                                                    new ConditionCanStep("RIGHT"),
                                                    new ActionMoveForward(null),
                                                    null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().isEmpty(), "no animations if the condition is false");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test if statements with true")
    @Test
    public void testIfStatementsTrue() {
        Program thisProgram = new Program(List.of(new ActionIf(
                new ConditionCanStep("FORWARD"),
                new ActionMoveForward(null),
                null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertFalse(result.getAnimations().isEmpty(), "add animations if the condition is false");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test if statements with invalid condition")
    @Test
    public void testIfStatementsInvalid() {
        Program thisProgram = new Program(List.of(new ActionIf(
                new ConditionCanStep("ASD"),
                new ActionMoveForward(null),
                null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.hasErrors(), "The level validation should have errors");
    }


    @DisplayName("test if-else statements with false")
    @Test
    public void testIfElseStatementsFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                    new ConditionCanStep("BACKWARD"),
                                                    new ActionMoveForward(null),
                                                    new ActionTurnRight(null),
                                                    null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_RIGHT), "should turn right if the condition is false");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test if-else statements with true")
    @Test
    public void testIfElseStatementsTrue() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionCanStep("LEFT"),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnRight(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD), "should move forward if the condition is false");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test while loop with false")
    @Test
    public void testWhileLoopFalse() {
        Program thisProgram = new Program(List.of(new ActionWhile(
                                                        new ConditionFalse(),
                                                        new ActionMoveForward(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().isEmpty(), "no animations if the condition is false");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test while loop with true")
    @Test
    public void testWhileLoopTrue() {
        Program thisProgram = new Program(List.of(new ActionWhile(
                                                        new ConditionCanStep("FORWARD"),
                                                        new ActionMoveForward(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD), "should move forward if the condition is true");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }

    @DisplayName("test infinite while loop with true")
    @Test
    public void testWhileLoopTrueTimeout() {
        Program thisProgram = new Program(List.of(new ActionWhile(
                                                        new ConditionTrue(),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertFalse(result.isCompleted(), "The level should be completed");
        assertTrue(result.hasErrors(), "The level validation should have errors");
    }

    @DisplayName("test for loop")
    @Test
    public void testForLoop() {
        Program thisProgram = new Program(List.of(new ActionLoop(
                                                        4,
                                                        new ActionIfElse(
                                                                new ConditionContainsCoin(),
                                                                new ActionCollectCoin(null),
                                                                new ActionMoveForward(null),
                                                                null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
        assertFalse(result.hasErrors(), "The level validation should not have errors");
    }


    @DisplayName("test infinite recursion")
    @Test
    public void testInfiniteRecursion() {
        Program thisProgram = new Program(List.of(new FunctionDefinition("f", new ActionFunctionCall("f", null)),
                                                  new ActionFunctionCall("f", null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.hasErrors(), "The level validation should have errors");
    }

    @DisplayName("test boolean or")
    @Test
    public void testBooleanOr() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionOR(
                                                                new ConditionTrue(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean or with false")
    @Test
    public void testBooleanOrFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionOR(
                                                                new ConditionFalse(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_LEFT),
                "should turn left if the condition is false");
    }

    @DisplayName("test boolean or with false")
    @Test
    public void testBooleanOrFalseTrue() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionOR(
                                                                new ConditionFalse(),
                                                                new ConditionTrue()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean not")
    @Test
    public void testBooleanNot() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionNOT(
                                                                new ConditionTrue()),
                                                        new ActionTurnLeft(null),
                                                        new ActionMoveForward(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should turn left if the condition is true");
    }

    @DisplayName("test boolean not with false")
    @Test
    public void testBooleanNotFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionNOT(
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean xor")
    @Test
    public void testBooleanXor() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionXOR(
                                                                new ConditionTrue(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should turn left if the condition is true");
    }

    @DisplayName("test boolean xor with false")
    @Test
    public void testBooleanXorFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionXOR(
                                                                new ConditionFalse(),
                                                                new ConditionTrue()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean xor with true")
    @Test
    public void testBooleanXorTrue() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionXOR(
                                                                new ConditionTrue(),
                                                                new ConditionTrue()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_LEFT),
                "should turn left if the condition is true");
    }

    @DisplayName("test boolean xor with true and false")
    @Test
    public void testBooleanXorTrueFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionXOR(
                                                                new ConditionTrue(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean and")
    @Test
    public void testBooleanAnd() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionAND(
                                                                new ConditionTrue(),
                                                                new ConditionTrue()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.MOVE_FORWARD),
                "should move forward if the condition is true");
    }

    @DisplayName("test boolean and with false")
    @Test
    public void testBooleanAndFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionAND(
                                                                new ConditionTrue(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_LEFT),
                "should turn left if the condition is true");
    }

    @DisplayName("test boolean and with false and true")
    @Test
    public void testBooleanAndFalseTrue() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionAND(
                                                                new ConditionFalse(),
                                                                new ConditionTrue()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));
        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_LEFT),
                "should turn left if the condition is true");
    }

    @DisplayName("test boolean and with false and false")
    @Test
    public void testBooleanAndFalseFalse() {
        Program thisProgram = new Program(List.of(new ActionIfElse(
                                                        new ConditionAND(
                                                                new ConditionFalse(),
                                                                new ConditionFalse()),
                                                        new ActionMoveForward(null),
                                                        new ActionTurnLeft(null),
                                                        null)));

        LevelValidation result = thisProgram.execute(context);
        assertTrue(result.getAnimations().contains(ERobotAnimation.TURN_LEFT),
                "should turn left if the condition is true");
    }




    @DisplayName("test teleport")
    @Test
    public void testTeleport() {
        // create a context playable with teleports
        robot = new Robot(0,0,EOrientation.DOWN);
        List<Tile> grid = List.of(
                new GrassTile(0,0,0),
                new TeleportTile(0,1,0, false, 3, 1, 0, 1),
                new TeleportTile(3,1,0, false, 3, 0, 0, 1)
        );
        List<Item> items = List.of(new CoinItem(0,0),
                new CoinItem(3,1));
        board = new Board(grid,items,2);
        List<ECategory> allowedCommands = List.of(ECategory.BASIC_COMMANDS, ECategory.CONDITIONS, ECategory.LOGIC);
        level = new Level("Level 1", "the first level",1, EWorld.PURGATORY,
                4,board,robot,allowedCommands,"../../assets/thumbnail.jpg");
        levelValidation = new LevelValidation();
        Context thisContext = new Context(board, robot, 4, levelValidation);

        Program thisProgram = new Program(List.of(new ActionCollectCoin(new ActionMoveForward(new ActionCollectCoin(null)))));

        LevelValidation result = thisProgram.execute(thisContext);

        assertFalse(result.hasErrors(), "The level validation should not have errors");
        assertTrue(result.isCompleted(), "The level should be completed");
    }

    @Nested
    public class testAfterTeleportCreation {

        private Context context;

        @BeforeEach
        void setup() {
            // create a context playable with teleports
            LanguageTests.this.robot = new Robot(0,0,EOrientation.DOWN);
            List<Tile> grid = List.of(
                    new LeverTile(0,0,0, 0, 3, 0),
                    new LeverTile(0,1,0, 0, 0, 0),
                    new LeverTile(0,2,0, 3, 1, 0),
                    new LeverTile(1,1,0, 2, 1, 0),
                    new LeverTile(1,0,0, 2, 2, 0),
                    new TeleportTile(0,3,0, false, 3, 1, 0, 1),
                    new TeleportTile(2,1,0, false, 4, 2, 0, 1),
                    new TeleportTile(2,2,0, false, 2, 1, 0, 0),
                    new GrassTile(4,2,0),
                    new TeleportTile(3,1,0, true, 3, 0, 0, 1)
            );
            List<Item> items = List.of(new CoinItem(3,1));
            Board board = new Board(grid,items,1);
            List<ECategory> allowedCommands = List.of(ECategory.BASIC_COMMANDS, ECategory.CONDITIONS, ECategory.LOGIC);
            LanguageTests.this.level = new Level("Level 1", "the first level",1, EWorld.PURGATORY,
                    6, LanguageTests.this.board,robot,allowedCommands,"../../assets/thumbnail.jpg");
            levelValidation = new LevelValidation();
            context = new Context(board, robot, level.getMaxCommandsNumber(), levelValidation);
        }

        @DisplayName("test active teleport with lever")
        @Test
        public void testActiveTeleportWithLever() {

            Program thisProgram = new Program(List.of(new ActionActivateLever(new ActionMoveForward(
                    new ActionMoveForward(new ActionMoveForward(new ActionCollectCoin(null)))))));

            assertFalse(context.getBoard().getTeleportAt(0, 3).isActive());

            LevelValidation result = thisProgram.execute(context);

            assertTrue(context.getBoard().getTeleportAt(0, 3).isActive());
            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertTrue(result.isCompleted(), "The level should be completed");
        }

        @DisplayName("test deactivate teleport with lever")
        @Test
        public void testDeactivateTeleportWithLever() {

            Program thisProgram = new Program(List.of(new ActionActivateLever(
                    new ActionActivateLever(new ActionMoveForward(new ActionCollectCoin(null))))));

            assertFalse(context.getBoard().getTeleportAt(0, 3).isActive());

            LevelValidation result = thisProgram.execute(context);

            assertFalse(context.getBoard().getTeleportAt(0, 3).isActive());

            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
            var animations = result.getAnimations();
            assertTrue(animations.get(0).toString().startsWith(ECoordinatesAnimation.ACTIVATE_LEVER.getName()),
                    "First animation does not match the expected one after lever pulled");
            assertTrue(animations.get(1).toString().startsWith(ECoordinatesAnimation.ACTIVATE_TELEPORT_AT.getName()),
                    "Second animation does not match the expected one after lever pulled");
            assertTrue(animations.get(2).toString().startsWith(ECoordinatesAnimation.ACTIVATE_TELEPORT_AT.getName()),
                    "Third animation does not match the expected one after lever pulled");
            assertTrue(animations.get(3).toString().startsWith(ECoordinatesAnimation.DEACTIVATE_LEVER.getName()),
                    "Fourth animation does not match the expected one after lever pulled");
            assertTrue(animations.get(4).toString().startsWith(ECoordinatesAnimation.DEACTIVATE_TELEPORT_AT.getName()),
                    "Fifth animation does not match the expected one after lever pulled");
            assertTrue(animations.get(5).toString().startsWith(ECoordinatesAnimation.DEACTIVATE_TELEPORT_AT.getName()),
                    "Sixth animation does not match the expected one after lever pulled");
        }

        @DisplayName("test activate teleport with lever pointing to a not teleport tile")
        @Test
        public void testActivateTeleportWithLeverWrong() {
            Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionActivateLever(null))));

            LevelValidation result = thisProgram.execute(context);

            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
        }

        @DisplayName("should not activate the teleport and give error when pulling a lever on a tile that is not a lever")
        @Test
        public void testActivateLeverOnNotLeverTile() {
            Program thisProgram = new Program(List.of(new ActionMoveForward(
                    new ActionMoveForward(new ActionMoveForward(new ActionActivateLever(null))))));

            LevelValidation result = thisProgram.execute(context);

            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
            assertTrue(result.getAnimations().contains(ERobotAnimation.EMOTE_NO),
                    "the level validation should contain an emote_no animation");
        }

        @DisplayName("should not activate teleport with lever pointing to a teleport pointing to a not teleport tile")
        @Test
        public void testActivateTeleportWithLeverToWrongTeleport() {
            Program thisProgram = new Program(List.of(new ActionMoveForward(
                    new ActionTurnLeft(new ActionMoveForward(new ActionActivateLever(
                            new ActionMoveForward(null)))))));

            LevelValidation result = thisProgram.execute(context);

            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
        }

        @DisplayName("should activate teleport with lever pointing to a teleport pointing to a not teleport tile")
        @Test
        public void testActivateLeverAfterError() {
            Program thisProgram = new Program(List.of(new ActionTurnLeft(new ActionMoveForward(
                    new ActionMoveForward(new ActionMoveForward(
                            new ActionActivateLever(null)))))));

            LevelValidation result = thisProgram.execute(context);

            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
        }

        @DisplayName("should not activate teleport if collecting a coin but teleport needs lever")
        @Test
        public void testActivateTeleportAfterCollectCoin() {
            Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionMoveForward(new ActionTurnLeft(
                    new ActionMoveForward(new ActionMoveForward(
                            new ActionCollectCoin(null))))))));

            LevelValidation result = thisProgram.execute(context);

            var tile = (TeleportTile) context.getBoard().getTileAt(2,2);

            assertFalse(tile.isActive(),"the teleport should still be not active after collecting a coin");
            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
        }

        @DisplayName("should not activate teleport if collecting a coin but teleport needs lever")
        @Test
        public void testActivateTeleport() {
            Program thisProgram = new Program(List.of(new ActionMoveForward(new ActionMoveForward(new ActionTurnLeft(
                    new ActionMoveForward(new ActionMoveForward(
                            new ActionCollectCoin(null))))))));

            LevelValidation result = thisProgram.execute(context);
            var tile = (TeleportTile) context.getBoard().getTileAt(2,2);

            assertFalse(tile.isActive(),"the teleport should still be not active after collecting a coin");
            assertFalse(result.hasErrors(), "The level validation should not have errors");
            assertFalse(result.isCompleted(), "The level should not be completed");
        }
    }

}
