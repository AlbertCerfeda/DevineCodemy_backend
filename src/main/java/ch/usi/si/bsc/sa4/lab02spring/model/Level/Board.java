package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import ch.usi.si.bsc.sa4.lab02spring.model.Item.CoinItem;
import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.GrassTile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;

import java.util.ArrayList;
import java.util.Random;


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

    /**
     * Generate board from given dimensions, start coordinates, how many steps to perform, how many items to place and the maximum elevation we can reach.
     * @param dim_x the x dimension of the board.
     * @param dim_y the y dimension of the board.
     * @param start_x the x position.
     * @param start_y the y position.
     * @param n_steps the number of steps to perform.
     * @param n_items the number of items to place.
     * @param max_elevation the maximum elevation we can reach.
     */
    public Board(final int dim_x, final int dim_y, final int start_x, final int start_y, final int n_steps, final int n_items, final int max_elevation) {
        Tile[][] grid = new Tile[dim_x][dim_y];
        Item[][] items = new Item[dim_x][dim_y];
        int placed_items = 0;
        Random random = new Random();

        // at the beginning all the grid is walkable
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // TODO: choose random walkable tile, for now only GRASS
                grid[i][j] = new GrassTile(i,j,0);
            }
        }

        // generate path
        ArrayList<Tile> path = new ArrayList<>();
        Tile currentTile = grid[start_x][start_y];
        path.add(currentTile);
        Tile previousTile = currentTile;

        // choose direction
        EOrientation currentDirection = EOrientation.getRandom();
        EOrientation previousDirection = currentDirection;
        ArrayList<Tile> turns = new ArrayList<>(); // save all turns;

        // start walking
        for (int step = 0; step < n_steps; step++) {
            currentTile = getNextTileFromPositionAndDirection(previousTile.getPos_x(), previousTile.getPos_y(), currentDirection);
            path.add(currentTile);

            if(currentDirection != previousDirection) {
                turns.add(currentTile);
            }

            // TODO: weight new direction based on previous direction
            //  continuing straight is more likely than turning, turning back is unlikely but possible.
            previousDirection = currentDirection;
            currentDirection = EOrientation.getRandom();
        }

        // place n_items items on the board (first in the corners of the path, then randomly on the board)
        // first item at the end of the path;
        ArrayList<Tile> itemsPath = new ArrayList<>(path); // duplicate path
        int new_item_x = currentTile.getPos_x();
        int new_item_y = currentTile.getPos_y();
        items[new_item_x][new_item_y] = new CoinItem(new_item_x, new_item_y); // TODO: pick random item, for now we just have coins.
        itemsPath.remove(currentTile); // remove tiles that already have items.
        placed_items++; // update placed_items.

        // place items in the corners of the path
        while (placed_items < n_items && turns.size() > 0 && itemsPath.size() > 0) {
            // pick random tile from turns.
            final int index = random.nextInt(turns.size() - 1);
            Tile thisTile = turns.get(index);
            new_item_x = thisTile.getPos_x();
            new_item_y = thisTile.getPos_y();
            items[new_item_x][new_item_y] = new CoinItem(new_item_x, new_item_y); // create new item
            // update lists and items count
            turns.remove(thisTile); // remove tiles that already have items.
            itemsPath.remove(thisTile); // remove tiles that already have items.
            placed_items++;
        }

        // place items randomly on the path
        while (placed_items < n_items && itemsPath.size() > 0) {
            // pick random tile on the path.
            final int index = random.nextInt(itemsPath.size() - 1);
            Tile thisTile = itemsPath.get(index);
            new_item_x = thisTile.getPos_x();
            new_item_y = thisTile.getPos_y();
            items[new_item_x][new_item_y] = new CoinItem(new_item_x, new_item_y); // create new item
            // update list and items count
            itemsPath.remove(thisTile); // remove tiles that already have items.
            placed_items++;
        }


        // TODO: generate obstacles (e.g. rivers, lakes, etc).
        //  Check if they interfere with the path and fix it accordingly: place a bridge, ...


        // adjust tile elevation
        int prev_elevation = 0;
        for (int i = 0; i < path.size()-1; i++) {
            final int delta = random.nextInt(3) -1; // pick random delta between -1, 0 and 1
            int new_elevation = prev_elevation + delta;
            new_elevation = (new_elevation > max_elevation || new_elevation < 0) ? prev_elevation : new_elevation;

            Tile current_tile = path.get(i);
            Tile next_tile = path.get(i+1);

            if (current_tile.isVisited()) {
                // do nothing, leave current elevation
                continue;
            } else if (next_tile.isVisited()) {
                int delta_elevation = next_tile.getPos_z() - prev_elevation;
                if (Math.abs(delta_elevation) > 2) {
                    // step too high, retry
                    i = i - delta_elevation;
                    continue;
                }

                if (new_elevation - next_tile.getPos_z() == 2) {
                    new_elevation = new_elevation - 1;
                } else if (new_elevation - next_tile.getPos_z() == -2) {
                    new_elevation = new_elevation + 1;
                }
            }
            // update tile
            current_tile.setPos_z(new_elevation);
            current_tile.setVisited(true);
            prev_elevation = new_elevation;
        }

        // set elevation for last tile
        final int delta = random.nextInt(3) -1;
        int new_elevation = prev_elevation + delta;
        new_elevation = (new_elevation > max_elevation || new_elevation < 0) ? prev_elevation : new_elevation;
        Tile last_tile = path.get(path.size()-1);
        last_tile.setPos_z(new_elevation);
        last_tile.setVisited(true);


        this.dim_x = dim_x;
        this.dim_y = dim_y;
        this.grid = grid;
        this.items = items;
    }



    public Tile getTileInPosition(final int x, final int y) {
        return grid[x][y];
    }

    public Tile getNextTileFromPositionAndDirection(final int x, final int y, EOrientation direction) {
        // TODO: handle case requesting bad position or new tile coordinates out of the board
        int new_x = x + direction.getDelta_x();
        int new_y = y + direction.getDelta_y();
        return grid[new_x][new_y];
    }

    public boolean canStep(final int x, final int y, EOrientation direction) {
        // FIXME
        return true;
    }
}
