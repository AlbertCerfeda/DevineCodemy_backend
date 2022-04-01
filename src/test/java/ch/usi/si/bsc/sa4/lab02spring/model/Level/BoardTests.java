package ch.usi.si.bsc.sa4.lab02spring.model.Level;

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

    public static Stream<Arguments> dimConstructorTestsArgumentProvider() {
        return Stream.of(
                arguments(5, 0, true), // test when dimY is zero
                arguments(0, 5, true), // test when dimX is zero
                arguments(1, -3, true), // test when dimY is negative
                arguments(-3, 3, true), // test when dimX is negative
                arguments(0, 0, true), // test when both are zero
                arguments(-3, -2, true), // test when both are negative
                arguments(3, 3, false) // test when both are positive
        );
    }

    @ParameterizedTest(name = "the board constructed with {0} and {1} should {2} throw")
    @MethodSource("dimConstructorTestsArgumentProvider")
    void dimConstructorTest(int dimX, int dimY, boolean shouldThrow) {
        if (shouldThrow) {
            assertThrows(Exception.class, () -> new Board(dimX, dimY), "constructor should throw");
        } else {
            assertDoesNotThrow(() -> new Board(dimX, dimY), "constructor should not throw");
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
