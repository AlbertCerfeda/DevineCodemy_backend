package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;

/**
 * A Board represents the terrain on which the player moves.
 * It also contains the items that are distributed on the board.
 */
public class Board {
    private final int dim_x;
    private final int dim_y;
    private Tile[][] grid;
    private Item[][] items;

    /**
     * Constructor for board objects
     * @param dim_x the x dimension of the board.
     * @param dim_y the y dimension of the board.
     * @param grid the grid representing the terrain.
     * @param items the items to collect.
     */
    public Board(final int dim_x, final int dim_y, Tile[][] grid, Item[][] items) {
        // TODO: calculate dim_x and dim_y based on grid size
        // TODO: Throw exception if 'grid' an 'items' dont have the same size
        this.dim_x = dim_x;
        this.dim_y = dim_y;
        this.grid = grid;
        this.items = items;
    }

    // TODO: generate board given dimensions


    public Tile getTileInPosition(final int x, final int y) {
        return grid[x][y];
    }

    public boolean canStep(final int x, final int y) {
        return true;
    }
}
