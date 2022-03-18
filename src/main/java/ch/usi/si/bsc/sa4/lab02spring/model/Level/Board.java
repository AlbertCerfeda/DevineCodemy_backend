package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.model.Item.EItem;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.ETile;

public class Board {
    private int dim_x;
    private int dim_y;
    private final ETile[][] grid;
    private final EItem[][] items;

    public Board(int dim_x, int dim_y, ETile[][] grid, EItem[][] items) {
        // TODO: calculate dim_x and dim_y based on grid size
        // TODO: Throw exception if 'grid' an 'items' dont have the same size
        this.dim_x = dim_x;
        this.dim_y = dim_y;
        this.grid = grid;
        this.items = items;
    }

    // TODO: generate board given dimensions
//    public static Board from(int dim_x, int dim_y) {
//        TileTypes[][] grid = new TileTypes[dim_x][dim_y];
//        ItemTypes[][] items = new ItemTypes[dim_x][dim_y];
//        CommandTypes[] commands = new CommandTypes[0];
//        // ...
//        return new Board(dim_x, dim_y, grid, items, commands);
//    }

    public ETile getTileTyleFromPosition(final int x, final int y) {
        return grid[x][y];
    }

    public boolean canStep(final int x, final int y) {
        return true;
    }
}
