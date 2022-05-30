package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The EWorld")
public class EWorldTests {

    EWorld inferno;
    EWorld purgatory;
    EWorld paradise;

    @BeforeEach
    void setup() {
        inferno = EWorld.INFERNO;
        purgatory = EWorld.PURGATORY;
        paradise = EWorld.PARADISE;
    }

    @DisplayName("should be able to get the EWorld of a given string")
    @Test
    public void testGetEWorldFromString() {
        var actualWorld1 = EWorld.getEWorldFromString("Inferno");
        var actualWorld2 = EWorld.getEWorldFromString("Purgatory");
        var actualWorld3 = EWorld.getEWorldFromString("Paradise");
        assertEquals(inferno,actualWorld1,
                "EWorld of Inferno is not INFERNO");
        assertEquals(purgatory,actualWorld2,
                "EWorld of PURGATORY is not PURGATORY");
        assertEquals(paradise,actualWorld3,
                "EWorld of PARADISE is not PARADISE");
    }

    @DisplayName("should throw when getting the EWorld of an unknown world")
    @Test
    public void testGetEWorldFromStringThrows() {
        Executable actualResult = ()-> EWorld.getEWorldFromString("");
        assertThrows(IllegalArgumentException.class,actualResult,
                "EWorld of empty doesn't throw");
    }

    @DisplayName("should be able to get description of any EWorld")
    @Test
    public void testGetDescriptionMessage() {
        var actualDescription1 = inferno.getDescriptionMessage();
        var actualDescription2 = purgatory.getDescriptionMessage();
        var actualDescription3 = paradise.getDescriptionMessage();
        assertNotNull(actualDescription1,
                "description is null after creating inferno");
        assertNotNull(actualDescription2,
                "description is null after creating purgatory");
        assertNotNull(actualDescription3,
                "description is null after creating paradise");
    }

    @DisplayName("should be able to get the congrats message of any EWorld")
    @Test
    public void testGetCongratsMessage() {
        var actualDescription1 = inferno.getCongratulationsMessage();
        var actualDescription2 = purgatory.getCongratulationsMessage();
        var actualDescription3 = paradise.getCongratulationsMessage();
        assertNotNull(actualDescription1,
                "congrats message is null after creating inferno");
        assertNotNull(actualDescription2,
                "congrats message is null after creating purgatory");
        assertNotNull(actualDescription3,
                "congrats message is null after creating paradise");
    }

    @DisplayName("should be able to get the display name of any EWorld")
    @Test
    public void testGetDisplayName() {
        var actualName1 = inferno.getCongratulationsMessage();
        var actualName2 = purgatory.getCongratulationsMessage();
        var actualName3 = paradise.getCongratulationsMessage();
        assertNotNull(actualName1,
                "display name is null after creating inferno");
        assertNotNull(actualName2,
                "display name is null after creating purgatory");
        assertNotNull(actualName3,
                "display name is null after creating paradise");
    }

    @DisplayName("should be able to get the world number of any EWorld")
    @Test
    public void testGetWorldNumber() {
        var actualWorldNumber1 = inferno.getWorldNumber();
        var actualWorldNumber2 = purgatory.getWorldNumber();
        var actualWorldNumber3 = paradise.getWorldNumber();
        assertEquals(1,actualWorldNumber1,
                "world number is null after creating inferno");
        assertEquals(2,actualWorldNumber2,
                "world number is null after creating purgatory");
        assertEquals(3,actualWorldNumber3,
                "world number is null after creating paradise");
    }

    @DisplayName("should be able to get the dto of any EWorld")
    @Test
    public void testToEWorldDTO() {
        var earthDTO = inferno.toEWorldDTO(Pair.of(1,5));
        var skyDTO = purgatory.toEWorldDTO(Pair.of(6,10));
        var lavaDTO = paradise.toEWorldDTO(Pair.of(11,15));
        assertEquals(inferno.getWorldNumber(),earthDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(inferno.getDisplayName(),earthDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(inferno.getDescriptionMessage(),earthDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(inferno.getCongratulationsMessage(),earthDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(1,earthDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(5,earthDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
        assertEquals(purgatory.getWorldNumber(),skyDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(purgatory.getDisplayName(),skyDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(purgatory.getDescriptionMessage(),skyDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(purgatory.getCongratulationsMessage(),skyDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(6,skyDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(10,skyDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
        assertEquals(paradise.getWorldNumber(),lavaDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(paradise.getDisplayName(),lavaDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(paradise.getDescriptionMessage(),lavaDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(paradise.getCongratulationsMessage(),lavaDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(11,lavaDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(15,lavaDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
    }

}
