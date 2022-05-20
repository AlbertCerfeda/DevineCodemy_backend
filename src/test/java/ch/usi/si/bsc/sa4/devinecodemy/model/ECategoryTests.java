package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The EAction")
public class ECategoryTests {

    ECategory basicCommands;
    ECategory logic;
    ECategory conditions;
    ECategory loops;
    ECategory functions;

    @BeforeEach
    void setup() {
        basicCommands = ECategory.BASIC_COMMANDS;
        logic = ECategory.LOGIC;
        conditions = ECategory.CONDITIONS;
        loops = ECategory.LOOPS;
        functions = ECategory.FUNCTIONS;
    }

    @DisplayName("should be able to return the function call name after creation")
    @Test
    public void testGetFuncCall() {
        var actualName1 = basicCommands.getName();
        var actualName2 = logic.getName();
        var actualName3 = conditions.getName();
        var actualName4 = loops.getName();
        var actualName5 = functions.getName();
        assertNotNull(actualName1,
                "the name of moveForward is null");
        assertNotNull(actualName2,
                "the name of turnLeft is null");
        assertNotNull(actualName3,
                "the name of turnRight is null");
        assertNotNull(actualName4,
                "the name of collectCoin is null");
        assertNotNull(actualName5,
                "the name of collectCoin is null");
    }

    @DisplayName("should be able to return the function call name after creation")
    @Test
    public void testGetDescription() {
        var actuallDescription1 = basicCommands.getDescription();
        var actuallDescription2 = logic.getDescription();
        var actuallDescription3 = conditions.getDescription();
        var actuallDescription4 = loops.getDescription();
        var actuallDescription5 = functions.getDescription();
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
        var basicCommandsDTO = basicCommands.toEActionDTO();
        var logicDTO = logic.toEActionDTO();
        var conditionsDTO = conditions.toEActionDTO();
        var loopsDTO = loops.toEActionDTO();
        var functionsDTO = functions.toEActionDTO();
        assertEquals(basicCommands.getDescription(),basicCommandsDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(basicCommands.getName(),basicCommandsDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(logic.getDescription(),logicDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(logic.getName(),logicDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(conditions.getDescription(),conditionsDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(conditions.getName(),conditionsDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(loops.getDescription(),loopsDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(loops.getName(),loopsDTO.getName(),
                "function call of the dto is not the same of the object of creation");
        assertEquals(functions.getDescription(),functionsDTO.getDescription(),
                "description of the dto is not the same of the object of creation");
        assertEquals(functions.getName(),functionsDTO.getName(),
                "function call of the dto is not the same of the object of creation");
    }

}
