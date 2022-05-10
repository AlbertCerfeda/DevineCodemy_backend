package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
@DisplayName("The Game player")
public class GamePlayerTests {

    GamePlayer gamePlayer;

    Level level;
    LevelValidation levelValidation;
    MockedStatic<EAction> action;

    @MockBean
    StatisticsRepository statisticsRepository;

    @BeforeEach
    void setup() {
        level = mock(Level.class);
        given(level.getLevelNumber()).willReturn(1);
        given(level.getAllowedCommands()).willReturn(List.of(EAction.MOVE_FORWARD));
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

    @DisplayName("should be able to return the parsed Commands")
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
        try (MockedStatic<EAction> action = mockStatic(EAction.class)) {
            action.when(()->EAction.getEActionFromCommand("moveForward")).thenReturn(EAction.MOVE_FORWARD);
            action.when(()->EAction.getEActionFromCommand("collectCoin")).thenReturn(EAction.COLLECT_COIN);
            action.when(()->EAction.getEActionFromCommand("turnLeft")).thenReturn(EAction.TURN_LEFT);
            action.when(()->EAction.getEActionFromCommand("turnRight")).thenReturn(EAction.TURN_RIGHT);
        }
        var actualValidation = gamePlayer.play(List.of("moveForward"));
        var expectedValidation = levelValidation;
        assertEquals(expectedValidation.isCompleted(),actualValidation.isCompleted(),
                "the completion is not the one expected");
    }


}
