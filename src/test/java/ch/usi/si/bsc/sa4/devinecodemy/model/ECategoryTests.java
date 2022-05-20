package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The EAction")
public class ECategoryTests {

    ECategory moveForward;
    ECategory turnLeft;
    ECategory turnRight;
    ECategory collectCoin;

    @BeforeEach
    void setup() {
        moveForward = ECategory.MOVE_FORWARD;
        turnLeft = ECategory.TURN_LEFT;
        turnRight = ECategory.TURN_RIGHT;
        collectCoin = ECategory.COLLECT_COIN;
    }

    @DisplayName("should be able to return the description after creation")
    @Test
    public void testGetEActionFromCommand() {
        var actualAction1 = ECategory.getEActionFromCommand("moveForward");
        var actualAction2 = ECategory.getEActionFromCommand("turnLeft");
        var actualAction3 = ECategory.getEActionFromCommand("turnRight");
        var actualAction4 = ECategory.getEActionFromCommand("collectCoin");
        assertEquals(moveForward,actualAction1,
                "EAction of moveForward is not MOVE_FORWARD");
        assertEquals(turnLeft,actualAction2,
                "EAction of turnLeft is not TURN_LEFT");
        assertEquals(turnRight,actualAction3,
                "EAction of turnRight is not TURN_RIGHT");
        assertEquals(collectCoin,actualAction4,
                "EAction of turnRight is not COLLECT_COIN");
    }

    @DisplayName("should throw when getting the EAction of an unknown world")
    @Test
    public void testGetEWorldFromStringThrows() {
        Executable actualResult = ()-> ECategory.getEActionFromCommand("");
        assertThrows(IllegalArgumentException.class,actualResult,
                "EAction of empty doesn't throw");
    }

    @DisplayName("should be able to return the function call name after creation")
    @Test
    public void testGetFuncCall() {
        var actualFuncCall1 = moveForward.getFuncCall();
        var actualFuncCall2 = turnLeft.getFuncCall();
        var actualFuncCall3 = turnRight.getFuncCall();
        var actualFuncCall4 = collectCoin.getFuncCall();
        assertNotNull(actualFuncCall1,
                "the funcCall of moveForward is null");
        assertNotNull(actualFuncCall2,
                "the funcCall of turnLeft is null");
        assertNotNull(actualFuncCall3,
                "the funcCall of turnRight is null");
        assertNotNull(actualFuncCall4,
                "the funcCall of collectCoin is null");
    }

    @DisplayName("should be able to return the function call name after creation")
    @Test
    public void testGetDescription() {
        var actuallDescription1 = moveForward.getDescription();
        var actuallDescription2 = turnLeft.getDescription();
        var actuallDescription3 = turnRight.getDescription();
        var actuallDescription4 = collectCoin.getDescription();
        assertNotNull(actuallDescription1,
                "the description of moveForward is null");
        assertNotNull(actuallDescription2,
                "the description of turnLeft is null");
        assertNotNull(actuallDescription3,
                "the description of turnRight is null");
        assertNotNull(actuallDescription4,
                "the description of collectCoin is null");
    }

    @DisplayName("should be able to return the dto of the object")
    @Test
    public void testToEActionDTO() {
        var moveForwardDTO = moveForward.toEActionDTO();
        var turnLeftDTO = turnLeft.toEActionDTO();
        var turnRightDTO = turnRight.toEActionDTO();
        var collectCoinDTO = collectCoin.toEActionDTO();
        assertEquals(moveForward.getDescription(),moveForwardDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(moveForward.getFuncCall(),moveForwardDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(turnLeft.getDescription(),turnLeftDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(turnLeft.getFuncCall(),turnLeftDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(turnRight.getDescription(),turnRightDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(turnRight.getFuncCall(),turnRightDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(collectCoin.getDescription(),collectCoinDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(collectCoin.getFuncCall(),collectCoinDTO.getName(),
                "function call of the dto is not the same of the object of creation");
    }

}
