package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The EWorld")
public class EWorldTests {

    EWorld earth;
    EWorld sky;
    EWorld lava;

    @BeforeEach
    void setup() {
        earth = EWorld.INFERNO;
        sky = EWorld.PURGATORY;
        lava = EWorld.PARADISE;
    }

    @DisplayName("should be able to get the EWorld of a given string")
    @Test
    public void testGetEWorldFromString() {
        var actualWorld1 = EWorld.getEWorldFromString("Purgatory");
        var actualWorld2 = EWorld.getEWorldFromString("Paradise");
        var actualWorld3 = EWorld.getEWorldFromString("Inferno");
        assertEquals(earth,actualWorld1,
                "EWorld of PURGATORY is not PURGATORY");
        assertEquals(sky,actualWorld2,
                "EWorld of PARADISE is not PARADISE");
        assertEquals(lava,actualWorld3,
                "EWorld of INFERNO is not INFERNO");
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
        var actualDescription1 = earth.getDescriptionMessage();
        var actualDescription2 = sky.getDescriptionMessage();
        var actualDescription3 = lava.getDescriptionMessage();
        assertNotNull(actualDescription1,
                "description is null after creating earth");
        assertNotNull(actualDescription2,
                "description is null after creating sky");
        assertNotNull(actualDescription3,
                "description is null after creating lava");
    }

    @DisplayName("should be able to get the congrats message of any EWorld")
    @Test
    public void testGetCongratsMessage() {
        var actualDescription1 = earth.getCongratulationsMessage();
        var actualDescription2 = sky.getCongratulationsMessage();
        var actualDescription3 = lava.getCongratulationsMessage();
        assertNotNull(actualDescription1,
                "congrats message is null after creating earth");
        assertNotNull(actualDescription2,
                "congrats message is null after creating sky");
        assertNotNull(actualDescription3,
                "congrats message is null after creating lava");
    }

    @DisplayName("should be able to get the display name of any EWorld")
    @Test
    public void testGetDisplayName() {
        var actualName1 = earth.getCongratulationsMessage();
        var actualName2 = sky.getCongratulationsMessage();
        var actualName3 = lava.getCongratulationsMessage();
        assertNotNull(actualName1,
                "display name is null after creating earth");
        assertNotNull(actualName2,
                "display name is null after creating sky");
        assertNotNull(actualName3,
                "display name is null after creating lava");
    }

    @DisplayName("should be able to get the world number of any EWorld")
    @Test
    public void testGetWorldNumber() {
        var actualWorldNumber1 = earth.getWorldNumber();
        var actualWorldNumber2 = sky.getWorldNumber();
        var actualWorldNumber3 = lava.getWorldNumber();
        assertEquals(1,actualWorldNumber1,
                "world number is null after creating earth");
        assertEquals(2,actualWorldNumber2,
                "world number is null after creating sky");
        assertEquals(3,actualWorldNumber3,
                "world number is null after creating lava");
    }

    @DisplayName("should be able to get the dto of any EWorld")
    @Test
    public void testToEWorldDTO() {
        var earthDTO = earth.toEWorldDTO(Pair.of(1,5));
        var skyDTO = sky.toEWorldDTO(Pair.of(6,10));
        var lavaDTO = lava.toEWorldDTO(Pair.of(11,15));
        assertEquals(earth.getWorldNumber(),earthDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(earth.getDisplayName(),earthDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(earth.getDescriptionMessage(),earthDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(earth.getCongratulationsMessage(),earthDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(1,earthDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(5,earthDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
        assertEquals(sky.getWorldNumber(),skyDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(sky.getDisplayName(),skyDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(sky.getDescriptionMessage(),skyDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(sky.getCongratulationsMessage(),skyDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(6,skyDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(10,skyDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
        assertEquals(lava.getWorldNumber(),lavaDTO.getWorldNumber(),
                "world number of the dto is not the same of the object of creation");
        assertEquals(lava.getDisplayName(),lavaDTO.getName(),
                "display name of the dto is not the same of the object of creation");
        assertEquals(lava.getDescriptionMessage(),lavaDTO.getDescriptionMessage(),
                "description of the dto is not the same of the object of creation");
        assertEquals(lava.getCongratulationsMessage(),lavaDTO.getCongratulationsMessage(),
                "congrats message of the dto is not the same of the object of creation");
        assertEquals(11,lavaDTO.getFirstLevelNumber(),
                "the first level of the dto is not the same of the object of creation");
        assertEquals(15,lavaDTO.getLastLevelNumber(),
                "the last level of the dto is not the same of the object of creation");
    }

}
