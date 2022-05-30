package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The eOrientation")
public class EOrientationTests {

    EOrientation up;
    EOrientation down;
    EOrientation left;
    EOrientation right;

    @BeforeEach
    void setup() {
        up = EOrientation.UP;
        down = EOrientation.DOWN;
        left = EOrientation.LEFT;
        right = EOrientation.RIGHT;
    }

    @DisplayName("should be able to get the opposite after creation")
    @Test
    public void testGetOpposite() {
        var actualOpposite1 = up.getOpposite();
        var actualOpposite2 = down.getOpposite();
        var actualOpposite3 = left.getOpposite();
        var actualOpposite4 = right.getOpposite();
        assertEquals(down,actualOpposite1,
                "the opposite of up is not down");
        assertEquals(up,actualOpposite2,
                "the opposite of down is not up");
        assertEquals(right,actualOpposite3,
                "the opposite of right is not left");
        assertEquals(left,actualOpposite4,
                "the opposite of left is not right");
    }

    @DisplayName("should be able to turn left after creation")
    @Test
    public void testGetTurnLeft() {
        var actualLeft1 = up.turnLeft();
        var actualLeft2 = down.turnLeft();
        var actualLeft3 = left.turnLeft();
        var actualLeft4 = right.turnLeft();
        assertEquals(left,actualLeft1,
                "the left of up is not left");
        assertEquals(right,actualLeft2,
                "the left of down is not right");
        assertEquals(down,actualLeft3,
                "the left of left is not down");
        assertEquals(up,actualLeft4,
                "the left of right is not up");
    }

    @DisplayName("should be able to turn right after creation")
    @Test
    public void testGetTurnRight() {
        var actualRight1 = up.turnRight();
        var actualRight2 = down.turnRight();
        var actualRight3 = left.turnRight();
        var actualRight4 = right.turnRight();
        assertEquals(right,actualRight1,
                "the right of up is not right");
        assertEquals(left,actualRight2,
                "the right of down is not left");
        assertEquals(up,actualRight3,
                "the right of left is not up");
        assertEquals(down,actualRight4,
                "the right of right is not down");
    }

    @DisplayName("should be able to get the delta x after creation")
    @Test
    public void testDeltaX() {
        var actualDeltaX1 = up.getDeltaX();
        var actualDeltaX2 = down.getDeltaX();
        var actualDeltaX3 = left.getDeltaX();
        var actualDeltaX4 = right.getDeltaX();
        assertEquals(0,actualDeltaX1,
                "the delta x of up is not set up properly");
        assertEquals(0,actualDeltaX2,
                "the delta x of down is not set up properly");
        assertEquals(-1,actualDeltaX3,
                "the delta x of left is not set up properly");
        assertEquals(1,actualDeltaX4,
                "the delta x of right is not set up properly");
    }

    @DisplayName("should be able to get the delta y after creation")
    @Test
    public void testDeltaY() {
        var actualDeltaX1 = up.getDeltaY();
        var actualDeltaX2 = down.getDeltaY();
        var actualDeltaX3 = left.getDeltaY();
        var actualDeltaX4 = right.getDeltaY();
        assertEquals(-1,actualDeltaX1,
                "the delta y of up is not set up properly");
        assertEquals(1,actualDeltaX2,
                "the delta y of down is not set up properly");
        assertEquals(0,actualDeltaX3,
                "the delta y of left is not set up properly");
        assertEquals(0,actualDeltaX4,
                "the delta y of right is not set up properly");
    }
}
