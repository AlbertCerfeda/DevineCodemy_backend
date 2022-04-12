package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("The board")
public class BoardTests {

    private Board board;

    public static Stream<Arguments> getNextTileTestsArgumentProvider() {
        return Stream.of(
                arguments(-1, -2, null, true, 0, 0), // test when both are negative
                arguments(34, 333, null, true, 0, 0), // test when both are out of bounds
                arguments(-1, 0, null, true, 0, 0), // test when x is negative
                arguments(0, -5, null, true, 0, 0), // test when y is negative
                arguments(20, 0, null, true, 0, 0), // test when x is out of bounds
                arguments(0, 29, null, true, 0, 0), // test when y is out of bounds
                arguments(3, 4, EOrientation.UP, false, 3, 3), // test when yes next up
                arguments(3, 4, EOrientation.DOWN, false, 3, 5), // test when yes next down
                arguments(3, 4, EOrientation.LEFT, false, 2, 4), // test when yes next left
                arguments(3, 4, EOrientation.RIGHT, false, 4, 4), // test when yes next right
                arguments(0, 0, EOrientation.LEFT, true, 0, 0), // test when no next left
                arguments(0, 0, EOrientation.UP, true, 0, 0), // test when no next up
                arguments(9, 11, EOrientation.DOWN, true, 0, 0), // test when no next down
                arguments(9, 11, EOrientation.RIGHT, true, 0, 0) // test when no next right
        );
    }

    @ParameterizedTest(name = "the next tile of x {0}, y {1}, and orientation {2} should {3} throw or return tile with x {4} and y {5}")
    @MethodSource("getNextTileTestsArgumentProvider")
    void getNextTileTest(int x, int y, EOrientation orientation, boolean shouldThrow, int nx, int ny) {
        if (shouldThrow) {
            assertThrows(Exception.class, () -> board.getNextTileFromPositionAndDirection(x, y, orientation), "method should throw");
        } else {
            assertDoesNotThrow(() -> board.getNextTileFromPositionAndDirection(x, y, orientation), "method should not throw");
            final Tile actualTile = board.getNextTileFromPositionAndDirection(x, y, orientation);
            assertEquals(nx, actualTile.getPos_x(), "next tile does not have correct x");
            assertEquals(ny, actualTile.getPos_y(), "next tile does not have correct y");
        }
    }

    @BeforeEach
    void setup() {
        board = new Board(10, 12);
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("has the same dimX provided in the constructor")
        @Test
        void testGetDimX() {
            var actualDimX = board.getDim_x();
            var expectedDimX = 10;
            assertEquals(expectedDimX, actualDimX, "dimX is not the one provided in the constructor");
        }

        @DisplayName("has the same dimY provided in the constructor")
        @Test
        void testGetDimY() {
            var actualDimY = board.getDim_y();
            var expectedDimY = 12;
            assertEquals(expectedDimY, actualDimY, "dimY is not the one provided in the constructor");
        }

    }
}
