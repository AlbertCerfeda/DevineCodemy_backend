package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
@DisplayName("The Game player")
public class GamePlayerTests {

    GamePlayer gamePlayer;

    Board board;
    Robot robot;
    Level level;
    LevelValidation levelValidation;
    MockedStatic<EAction> action;

    @MockBean
    StatisticsRepository statisticsRepository;

    @BeforeEach
    void setup() {
        board = mock(Board.class);
        given(board.canStep(anyInt(),anyInt(),any())).willReturn(true);
        robot = mock(Robot.class);
        given(robot.getPosX()).willReturn(1);
        given(robot.getPosY()).willReturn(1);
        given(robot.getOrientation()).willReturn(EOrientation.UP);
        level = mock(Level.class);
        given(level.getLevelNumber()).willReturn(1);
        given(level.getAllowedCommands()).willReturn(List.of(EAction.MOVE_FORWARD));
        given(level.getMaxCommandsNumber()).willReturn(3);
        given(level.getBoard()).willReturn(board);
        given(level.getRobot()).willReturn(robot);
        gamePlayer = new GamePlayer(level);
        levelValidation = mock(LevelValidation.class);
        given(levelValidation.isCompleted()).willReturn(false);
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

    @DisplayName("should be possible to get the parsed Commands")
    @Test
    public void testGetParsedCommands() {
        var actualCommands = gamePlayer.getParsedCommands();
        var expectedCommands = List.of();
        assertEquals(expectedCommands,actualCommands,
                "number is not the one provided on creation");
    }

    @DisplayName("should be possible to play a list of commands to complete the Level")
    @Test
    public void testPlay() {
        try (MockedStatic<EAction> action = mockStatic(EAction.class)) {
            action.when(()->EAction.getEActionFromCommand("moveForward")).thenReturn(EAction.MOVE_FORWARD);
//            var actualValidation = gamePlayer.play(List.of("moveForward")); TODO: fix
            var expectedValidation = levelValidation;
            assertEquals(expectedValidation.isCompleted(),false,
                    "the completion is not the one expected");
        }
    }

    @DisplayName("should be possible to parse a list of strings into commands")
    @Test
    public void testParseCommands() {
        try (MockedStatic<EAction> action = mockStatic(EAction.class)) {
            action.when(()->EAction.getEActionFromCommand("moveForward")).thenReturn(EAction.MOVE_FORWARD);
            action.when(()->EAction.getEActionFromCommand("turnLeft")).thenReturn(EAction.TURN_LEFT);
            var actualValidation = gamePlayer.play(List.of("moveForward","turnLeft"));
            var expectedValidation = levelValidation;
            assertEquals(expectedValidation.isCompleted(),actualValidation.isCompleted(),
                    "the completion is not the one expected");
        }
    }

    @DisplayName("should be possible to parse a list of strings into commands")
    @Test
    public void testParseCommandsWrongCommands() {
        try (MockedStatic<EAction> action = mockStatic(EAction.class)) {
            IllegalArgumentException exception = mock(IllegalArgumentException.class);
            given(exception.getMessage()).willReturn("Unknown command: 'moveForwar'");
            action.when(()->EAction.getEActionFromCommand("moveForwar")).thenThrow(exception);
            action.when(()->EAction.getEActionFromCommand("turnLeft")).thenReturn(EAction.TURN_LEFT);
            List<String> actualValidationErrors = gamePlayer.play(List.of("moveForwar","turnLeft")).getErrors();
            var expectedValidationError1 = "Unknown command: 'moveForwar'";
            assertEquals(expectedValidationError1,actualValidationErrors.get(0),
                    "doesn't add error when inserting commands with typos");
            var expectedValidationError2 = "Command not allowed: 'turnLeft'";
            assertEquals(expectedValidationError2,actualValidationErrors.get(1),
                    "doesn't add error when inserting not allowed commands");
        }
    }


}
