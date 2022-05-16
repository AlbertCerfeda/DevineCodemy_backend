package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("The board")
public class BoardTests {

    private Board board;

    public static Stream<Arguments> getItemAtTestsArgumentProvider() {
        return Stream.of(
                arguments(-1, -2, true, null), // test when both are negative
                arguments(34, 333, true, null), // test when both are out of bounds
                arguments(-1, 0, true, null), // test when x is negative
                arguments(0, -5, true, null), // test when y is negative
                arguments(20, 0, true, null), // test when x is out of bounds
                arguments(0, 29, true, null), // test when y is out of bounds
                arguments(3, 4, false, null), // test when x and y are both within bounds but no item (x does not match)
                arguments(7, 4, false, null), // test when x and y are both within bounds but no item (x matches)
                arguments(4, 7, false, new CoinItem(4, 7)) // test when x and y are both within bounds and yes item
        );
    }

    @ParameterizedTest(name = "getting the item at x {0} and y {1} should {2} throw and return item {3}")
    @MethodSource("getItemAtTestsArgumentProvider")
    void getItemAtTest(int x, int y, boolean shouldThrow, Item expectedItem) {
        if (shouldThrow) {
            assertThrows(Exception.class, () -> board.getItemAt(x, y), "method should throw");
        } else {
            assertDoesNotThrow(() -> board.getItemAt(x, y), "method should not throw");
            final Item actualItem = board.getItemAt(x, y);
            if (expectedItem == null) {
                assertNull(actualItem);
            } else {
                assertEquals(expectedItem.getPosX(), actualItem.getPosX(), "item does not have correct x");
                assertEquals(expectedItem.getPosY(), actualItem.getPosY(), "item does not have correct y");
            }
        }
    }

    public static Stream<Arguments> getTileAtTestsArgumentProvider() {
        return Stream.of(
                arguments(-1, -2, true, Pair.of(0, 0)), // test when both are negative
                arguments(34, 333, true, Pair.of(0, 0)), // test when both are out of bounds
                arguments(-1, 0, true, Pair.of(0, 0)), // test when x is negative
                arguments(0, -5, true, Pair.of(0, 0)), // test when y is negative
                arguments(20, 0, true, Pair.of(0, 0)), // test when x is out of bounds
                arguments(0, 29, true, Pair.of(0, 0)), // test when y is out of bounds
                arguments(3, 4, false, Pair.of(3, 4)) // test when x and y are both within bounds
//                arguments(5, 8, false, null) // test when x and y are both within bounds but no tile
        );
    }

    @ParameterizedTest(name = "getting the tile at x {0} and y {1} should {2} throw and return tile with xy {3}")
    @MethodSource("getTileAtTestsArgumentProvider")
    void getTileAtTest(int x, int y, boolean shouldThrow, Pair<Integer, Integer> xy) {
        if (shouldThrow) {
            assertThrows(Exception.class, () -> board.getTileAt(x, y), "method should throw");
        } else {
            assertDoesNotThrow(() -> board.getTileAt(x, y), "method should not throw");
            final Tile actualTile = board.getTileAt(x, y);
            if (xy == null) {
                assertNull(actualTile, "tile should be null");
            } else {
                assertEquals(xy.getFirst(), actualTile.getPosX(), "tile does not have correct x");
                assertEquals(xy.getSecond(), actualTile.getPosY(), "tile does not have correct y");
            }
        }
    }

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
            assertEquals(nx, actualTile.getPosX(), "next tile does not have correct x");
            assertEquals(ny, actualTile.getPosY(), "next tile does not have correct y");
        }
    }

    public static Stream<Arguments> canStepTestsArgumentProvider() {
        return Stream.of(
                arguments(-1, -2, EOrientation.UP, false), // test when both are negative
                arguments(34, 333, EOrientation.UP, false), // test when both are out of bounds
                arguments(-1, 0, EOrientation.UP, false), // test when x is negative
                arguments(0, -5, EOrientation.UP, false), // test when y is negative
                arguments(20, 0, EOrientation.UP, false), // test when x is out of bounds
                arguments(0, 29, EOrientation.UP, false), // test when y is out of bounds
                arguments(3, 4, EOrientation.UP, false), // test when yes next up but start on water
                arguments(4, 3, EOrientation.LEFT, false), // test when yes next left and cannot step due to not step-able
                arguments(4, 3, EOrientation.RIGHT, false), // test when yes next right and cannot step due to high delta z
                arguments(4, 3, EOrientation.DOWN, true) // test when yes next down and can step
        );
    }

    @ParameterizedTest(name = "checking can step from x {0} and y {1} in direction {2} should return {3}")
    @MethodSource("canStepTestsArgumentProvider")
    void canStepTest(int x, int y, EOrientation direction, boolean expected) {
        final boolean actual = board.canStep(x, y, direction);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> containsItemAtTestsArgumentProvider() {
        return Stream.of(
                arguments(-1, -2, false), // test when both are negative
                arguments(34, 333, false), // test when both are out of bounds
                arguments(-1, 0, false), // test when x is negative
                arguments(0, -5, false), // test when y is negative
                arguments(20, 0, false), // test when x is out of bounds
                arguments(0, 29, false), // test when y is out of bounds
                arguments(3, 4, false), // test when x and y are both within bounds but no item
                arguments(4, 7, true) // test when x and y are both within bounds and yes item
        );
    }

    @ParameterizedTest(name = "checking if contains item at x {0} and y {1} should return {2}")
    @MethodSource("containsItemAtTestsArgumentProvider")
    void containsItemAtTest(int x, int y, boolean expected) {
        final boolean actual = board.containsItemAt(x, y);
        assertEquals(expected, actual);
    }

    @BeforeEach
    void setup() {
        var grid = Arrays.asList(
                new WaterTile(0, 0, 0),
                new WaterTile(1, 0, 0),
                new WaterTile(2, 0, 0),
                new WaterTile(3, 0, 0),
                new WaterTile(4, 0, 0),
                new WaterTile(5, 0, 0),
                new WaterTile(6, 0, 0),
                new WaterTile(7, 0, 0),
                new WaterTile(8, 0, 0),
                new WaterTile(9, 0, 0),

                new WaterTile(0, 1, 0),
                new WaterTile(1, 1, 0),
                new WaterTile(2, 1, 0),
                new WaterTile(3, 1, 0),
                new WaterTile(4, 1, 0),
                new WaterTile(5, 1, 0),
                new WaterTile(6, 1, 0),
                new WaterTile(7, 1, 0),
                new WaterTile(8, 1, 0),
                new WaterTile(9, 1, 0),

                new GrassTile(0, 2, 0),
                new WaterTile(1, 2, 0),
                new WaterTile(2, 2, 0),
                new WaterTile(3, 2, 0),
                new WaterTile(4, 2, 0),
                new WaterTile(5, 2, 0),
                new WaterTile(6, 2, 0),
                new WaterTile(7, 2, 0),
                new WaterTile(8, 2, 0),
                new GrassTile(9, 2, 0),

                new GrassTile(0, 3, 0),
                new WaterTile(1, 3, 0),
                new WaterTile(2, 3, 0),
                new WaterTile(3, 3, 0),
                new ConcreteTile(4, 3, 0),
                new ConcreteTile(5, 3, 2),
                new WaterTile(6, 3, 0),
                new WaterTile(7, 3, 0),
                new WaterTile(8, 3, 0),
                new GrassTile(9, 3, 0),

                new GrassTile(0, 4, 0),
                new WaterTile(1, 4, 0),
                new WaterTile(2, 4, 0),
                new WaterTile(3, 4, 0),
                new ConcreteTile(4, 4, 0),
                new WaterTile(5, 4, 0),
                new WaterTile(6, 4, 0),
                new WaterTile(7, 4, 0),
                new WaterTile(8, 4, 0),
                new GrassTile(9, 4, 0),

                new GrassTile(0, 5, 0),
                new WaterTile(1, 5, 0),
                new WaterTile(2, 5, 0),
                new WaterTile(3, 5, 0),
                new ConcreteTile(4, 5, 0),
                new WaterTile(5, 5, 0),
                new WaterTile(6, 5, 0),
                new WaterTile(7, 5, 0),
                new WaterTile(8, 5, 0),
                new GrassTile(9, 5, 0),

                new WaterTile(0, 6, 0),
                new WaterTile(1, 6, 0),
                new WaterTile(2, 6, 0),
                new WaterTile(3, 6, 0),
                new ConcreteTile(4, 6, 0),
                new WaterTile(5, 6, 0),
                new WaterTile(6, 6, 0),
                new WaterTile(7, 6, 0),
                new WaterTile(8, 6, 0),
                new GrassTile(9, 6, 0),

                new WaterTile(0, 7, 0),
                new WaterTile(1, 7, 0),
                new SandTile(2, 7, 0),
                new GrassTile(3, 7, 0),
                new GrassTile(4, 7, 0),
                new GrassTile(5, 7, 0),
                new GrassTile(6, 7, 0),
                new GrassTile(7, 7, 0),
                new GrassTile(8, 7, 0),
                new GrassTile(9, 7, 0),

                new WaterTile(0, 8, 0),
                new WaterTile(1, 8, 0),
                new WaterTile(2, 8, 0),
                new WaterTile(3, 8, 0),
                new WaterTile(4, 8, 0),
//                new WaterTile(5, 8, 0), // intentional
                new WaterTile(6, 8, 0),
                new WaterTile(7, 8, 0),
                new WaterTile(8, 8, 0),
                new GrassTile(9, 8, 0),

                new WaterTile(0, 9, 0),
                new WaterTile(1, 9, 0),
                new WaterTile(2, 9, 0),
                new WaterTile(3, 9, 0),
                new WaterTile(4, 9, 0),
                new WaterTile(5, 9, 0),
                new WaterTile(6, 9, 0),
                new WaterTile(7, 9, 0),
                new WaterTile(8, 9, 0),
                new GrassTile(9, 9, 0)
        );
        List<Item> items = List.of(
                new CoinItem(4, 7),
                new CoinItem(7, 7)
        );
        board = new Board(grid, items, 2);
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("has the same dimX provided in the constructor")
        @Test
        void testGetDimX() {
            var actualDimX = board.getDimX();
            var expectedDimX = 10;
            assertEquals(expectedDimX, actualDimX, "dimX is not the one provided in the constructor");
        }

        @DisplayName("has the same dimY provided in the constructor")
        @Test
        void testGetDimY() {
            var actualDimY = board.getDimY();
            var expectedDimY = 10;
            assertEquals(expectedDimY, actualDimY, "dimY is not the one provided in the constructor");
        }

        @DisplayName("has the same grid provided in the constructor")
        @Test
        void testGetGrid() {
            var actualGrid = board.getGrid();
            var expectedGrid = Arrays.asList(
                    new WaterTile(0, 0, 0),
                    new WaterTile(1, 0, 0),
                    new WaterTile(2, 0, 0),
                    new WaterTile(3, 0, 0),
                    new WaterTile(4, 0, 0),
                    new WaterTile(5, 0, 0),
                    new WaterTile(6, 0, 0),
                    new WaterTile(7, 0, 0),
                    new WaterTile(8, 0, 0),
                    new WaterTile(9, 0, 0),

                    new WaterTile(0, 1, 0),
                    new WaterTile(1, 1, 0),
                    new WaterTile(2, 1, 0),
                    new WaterTile(3, 1, 0),
                    new WaterTile(4, 1, 0),
                    new WaterTile(5, 1, 0),
                    new WaterTile(6, 1, 0),
                    new WaterTile(7, 1, 0),
                    new WaterTile(8, 1, 0),
                    new WaterTile(9, 1, 0),

                    new GrassTile(0, 2, 0),
                    new WaterTile(1, 2, 0),
                    new WaterTile(2, 2, 0),
                    new WaterTile(3, 2, 0),
                    new WaterTile(4, 2, 0),
                    new WaterTile(5, 2, 0),
                    new WaterTile(6, 2, 0),
                    new WaterTile(7, 2, 0),
                    new WaterTile(8, 2, 0),
                    new GrassTile(9, 2, 0),

                    new GrassTile(0, 3, 0),
                    new WaterTile(1, 3, 0),
                    new WaterTile(2, 3, 0),
                    new WaterTile(3, 3, 0),
                    new ConcreteTile(4, 3, 0),
                    new ConcreteTile(5, 3, 2),
                    new WaterTile(6, 3, 0),
                    new WaterTile(7, 3, 0),
                    new WaterTile(8, 3, 0),
                    new GrassTile(9, 3, 0),

                    new GrassTile(0, 4, 0),
                    new WaterTile(1, 4, 0),
                    new WaterTile(2, 4, 0),
                    new WaterTile(3, 4, 0),
                    new ConcreteTile(4, 4, 0),
                    new WaterTile(5, 4, 0),
                    new WaterTile(6, 4, 0),
                    new WaterTile(7, 4, 0),
                    new WaterTile(8, 4, 0),
                    new GrassTile(9, 4, 0),

                    new GrassTile(0, 5, 0),
                    new WaterTile(1, 5, 0),
                    new WaterTile(2, 5, 0),
                    new WaterTile(3, 5, 0),
                    new ConcreteTile(4, 5, 0),
                    new WaterTile(5, 5, 0),
                    new WaterTile(6, 5, 0),
                    new WaterTile(7, 5, 0),
                    new WaterTile(8, 5, 0),
                    new GrassTile(9, 5, 0),

                    new WaterTile(0, 6, 0),
                    new WaterTile(1, 6, 0),
                    new WaterTile(2, 6, 0),
                    new WaterTile(3, 6, 0),
                    new ConcreteTile(4, 6, 0),
                    new WaterTile(5, 6, 0),
                    new WaterTile(6, 6, 0),
                    new WaterTile(7, 6, 0),
                    new WaterTile(8, 6, 0),
                    new GrassTile(9, 6, 0),

                    new WaterTile(0, 7, 0),
                    new WaterTile(1, 7, 0),
                    new SandTile(2, 7, 0),
                    new GrassTile(3, 7, 0),
                    new GrassTile(4, 7, 0),
                    new GrassTile(5, 7, 0),
                    new GrassTile(6, 7, 0),
                    new GrassTile(7, 7, 0),
                    new GrassTile(8, 7, 0),
                    new GrassTile(9, 7, 0),

                    new WaterTile(0, 8, 0),
                    new WaterTile(1, 8, 0),
                    new WaterTile(2, 8, 0),
                    new WaterTile(3, 8, 0),
                    new WaterTile(4, 8, 0),
//                    new WaterTile(5, 8, 0), // intentional
                    new WaterTile(6, 8, 0),
                    new WaterTile(7, 8, 0),
                    new WaterTile(8, 8, 0),
                    new GrassTile(9, 8, 0),

                    new WaterTile(0, 9, 0),
                    new WaterTile(1, 9, 0),
                    new WaterTile(2, 9, 0),
                    new WaterTile(3, 9, 0),
                    new WaterTile(4, 9, 0),
                    new WaterTile(5, 9, 0),
                    new WaterTile(6, 9, 0),
                    new WaterTile(7, 9, 0),
                    new WaterTile(8, 9, 0),
                    new GrassTile(9, 9, 0)
            );
            assertEquals(expectedGrid, actualGrid, "grid is not the one provided in the constructor");
        }

        @DisplayName("has the same items provided in the constructor")
        @Test
        void testGetItems() {
            var actualItems = board.getItems();
            var expectedItems = List.of(
                    new CoinItem(4, 7),
                    new CoinItem(7, 7)
            );
            assertEquals(expectedItems, actualItems, "items is not the one provided in the constructor");
        }
        

        @DisplayName("has the same number of coins provided in the constructor")
        @Test
        void testGetNumberOfCoins() {
            var actualNumberOfCoins = board.getCoinsNumber();
            var expectedNumberOfCoins = 2;
            assertEquals(expectedNumberOfCoins, actualNumberOfCoins, "number of coins is not the one provided in the constructor");
        }

        @DisplayName("can return the correct boardDTO")
        @Test
        void testToBoardDTO() {
            var actualBoardDTO = board.toBoardDTO();
            assertEquals(board.getDimX(), actualBoardDTO.getDimX(), "boardDTO does not have the same dimX as its board");
            assertEquals(board.getDimY(), actualBoardDTO.getDimY(), "boardDTO does not have the same dimY as its board");
            assertNotNull(actualBoardDTO.getGrid(), "boardDTO grid is null");
            assertNotNull(actualBoardDTO.getItems(), "boardDTO items is null");
        }

        @DisplayName("can return the correct string of itself")
        @Test
        void testToString() {
            var actualString = board.toString();
            var expectedString = "WWGGGGWWWW\n" +
                    "WWWWWWWWWW\n" +
                    "WWWWWWWSWW\n" +
                    "WWWWWWWGWW\n" +
                    "WWWCCCC*WW\n" +
                    "WWWCWWWG W\n" +
                    "WWWWWWWGWW\n" +
                    "WWWWWWW*WW\n" +
                    "WWWWWWWGWW\n" +
                    "WWGGGGGGGG\n";
            assertEquals(expectedString, actualString, "strings don't match");
        }

    }
}
