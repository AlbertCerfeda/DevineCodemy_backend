package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        @DisplayName("can return the correct robotDTO")
        @Test
        void testToRobotDTO() {
            var actualRobotDTO = robot.toRobotDTO();
            assertEquals(robot.getPosX(), actualRobotDTO.getPosX(), "robotDTO does not have the same posX as its robot");
            assertEquals(robot.getPosY(), actualRobotDTO.getPosY(), "robotDTO does not have the same posY as its robot");
            assertEquals(robot.getOrientation().toString(), actualRobotDTO.getOrientation(), "robotDTO does not have the same orientation as its robot");
        }

        @DisplayName("can compare to another robot")
        @Test
        void testEquals() {
            var robot1 = new Robot(23,3,EOrientation.DOWN);
            assertEquals(robot,robot1,
                    "robots with same values are not equal");
            robot1 = new Robot(23,3,EOrientation.UP);
            assertNotEquals(robot,robot1,
                    "robot is equal when compared to a robot with same x,y but different orientation");
            robot1 = new Robot(23,2,EOrientation.DOWN);
            assertNotEquals(robot,robot1,
                    "robot is equal when compared to a robot with same x but different y");
            robot1 = new Robot(22,3,EOrientation.DOWN);
            assertNotEquals(robot,robot1,
                    "robot is equal when compared to a robot with different x");
            assertEquals(robot,robot,
                    "robot is not equal when compared to itself");
            robot1 = new Robot(23,3,EOrientation.DOWN);
            assertNotEquals(robot,robot1.toRobotDTO(),
                    "robot is equal when compared to a different class object");
        }
    }

}
