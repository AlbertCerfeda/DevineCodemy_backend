package ch.usi.si.bsc.sa4.devinecodemy.model.Level;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The robot")
public class RobotTests {

    private Robot robot;

    @BeforeEach
    void setup() {
        robot = new Robot(23, 3, EOrientation.DOWN);
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("has the same x provided in the constructor")
        @Test
        void testGetPosX() {
            var actualPosX = robot.getPosX();
            var expectedPosX = 23;
            assertEquals(expectedPosX, actualPosX, "posX is not the one provided in the constructor");
        }

        @DisplayName("has the same posY provided in the constructor")
        @Test
        void testGetPosY() {
            var actualPosY = robot.getPosY();
            var expectedPosY = 3;
            assertEquals(expectedPosY, actualPosY, "posY is not the one provided in the constructor");
        }

        @DisplayName("has the same orientation provided in the constructor")
        @Test
        void testGetMaxSteps() {
            var actualOrientation = robot.getOrientation();
            var expectedOrientation = EOrientation.DOWN;
            assertEquals(expectedOrientation, actualOrientation, "orientation is not the one provided in the constructor");
        }

    }

}
