package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import ch.usi.si.bsc.sa4.lab02spring.model.Item.CoinItem;
import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.BridgeTile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.GrassTile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.WaterTile;

import java.util.*;


/**
 * A Board representing a map.
 * Contains the terrain on which the player moves and the collectable items.
 */
public class Board {
    private int dim_x;
    private int dim_y;
    private List<Tile> grid;
    private List<Item> items;
    private int difficulty;
    private int n_coins;

    /**
     * Constructor for board objects.
     * @param grid the tiles of the board representing the terrain.
     * @param items the items present on the board.
     */
    public Board(List<Tile> grid, List<Item> items, int difficulty, int n_coins) {
        this.dim_x = 0;
        this.dim_y = 0;
        // Derives the dimensions of the board
        grid.forEach((t)-> this.dim_x = Math.max(this.dim_x, t.getPos_x() + 1));
        grid.forEach((t)-> this.dim_y = Math.max(this.dim_y, t.getPos_y() + 1));
        items.forEach((i)-> this.dim_x = Math.max(this.dim_x, i.getPos_x() + 1));
        items.forEach((i)-> this.dim_y = Math.max(this.dim_y, i.getPos_y() + 1));

        this.grid = grid;
        this.items = items;
        this.difficulty = difficulty;
        this.n_coins = n_coins;
    }


    /**
     * Generate board from given dimensions.
     * @param dim_x the x dimension of the board.
     * @param dim_y the y dimension of the board.
     * @throws IndexOutOfBoundsException if an error occurs, should never happen.
     */
    public Board(final int dim_x, final int dim_y) throws IndexOutOfBoundsException {
        Random rand = new Random();
        final int start_x = rand.nextInt(dim_x);
        final int start_y = rand.nextInt(dim_y);
        final int n_steps = rand.nextInt(3*dim_x + 3*dim_y);
        final int water_n_steps = rand.nextInt(2*dim_x*dim_y);
        final int n_items = rand.nextInt(n_steps/2);
        final int max_elevation = rand.nextInt((dim_x+dim_y)/3);
        init(dim_x, dim_y, start_x, start_y, n_steps, water_n_steps, n_items, max_elevation);
    }

    /**
     * Generate board from given dimensions.
     * @param dim_x the x dimension of the board.
     * @param dim_y the y dimension of the board.
     * @param start_x the starting x position.
     * @param start_y the starting y position.
     * @throws IndexOutOfBoundsException if an error occurs, should never happen.
     */
    public Board(final int dim_x, final int dim_y, final int start_x, final int start_y) throws IndexOutOfBoundsException {
        Random rand = new Random();
        final int n_steps = rand.nextInt(3*dim_x + 3*dim_y);
        final int water_n_steps = rand.nextInt(dim_x*dim_y);
        final int n_items = rand.nextInt(n_steps/2);
        final int max_elevation = rand.nextInt((dim_x+dim_y)/4);
        init(dim_x, dim_y, start_x, start_y, n_steps, water_n_steps, n_items, max_elevation);
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
     * @throws IndexOutOfBoundsException if an error occurs, should never happen.
     */
    public Board (final int dim_x, final int dim_y, final int start_x, final int start_y, final int n_steps, final int water_n_steps, final int n_items, final int max_elevation) throws IndexOutOfBoundsException {
        init(dim_x, dim_y, start_x, start_y, n_steps, water_n_steps, n_items, max_elevation);
    }


    /**
     * To automatically init board from given dimensions, start coordinates, how many steps to perform, how many items to place and the maximum elevation we can reach.
     * @param dim_x the x dimension of the board.
     * @param dim_y the y dimension of the board.
     * @param start_x the x position.
     * @param start_y the y position.
     * @param n_steps the number of steps to perform.
     * @param n_items the number of items to place.
     * @param max_elevation the maximum elevation we can reach.
     * @throws IndexOutOfBoundsException if an error occurs, should never happen.
     */
    private void init(final int dim_x, final int dim_y, final int start_x, final int start_y, final int n_steps, final int water_n_steps, final int n_items, final int max_elevation) throws IndexOutOfBoundsException {
        // SETUP BOARD ----------------------------------------------------------------------------------------
        this.dim_x = dim_x;
        this.dim_y = dim_y;
        Tile[][] grid = new Tile[dim_x][dim_y];
        Item[][] items =  new Item[dim_x][dim_y];
        this.difficulty = n_steps;
        this.n_coins = n_items;

        // INIT BOARD -----------------------------------------------------------------------------------------
        int placed_items = 0;
        Random random = new Random();

        // at the beginning all the grid is walkable
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // TODO: choose random walkable tile, for now only GRASS
                grid[i][j] = new GrassTile(i,j,0);
            }
        }

        // PATH ------------------------------------------------------------------------------------------------
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
            // get next water tile, if the requested tile is not valid (out of the board) recompute direction and retry
            int count = 0;
            while(true) {
                try {
                    // get valid tile and add it to the path
                    currentTile = getNextTileFromPositionAndDirection(grid, previousTile.getPos_x(), previousTile.getPos_y(), currentDirection);
                    path.add(currentTile);
                    break;  // break out of loop on success
                } catch (IndexOutOfBoundsException e) {
                    // recompute direction and retry, max 100 tries
                    if (++count == 100) throw e;
                    currentDirection = EOrientation.getRandom();
                }
            }

            // keep track of each turn
            if(currentDirection != previousDirection) {
                turns.add(currentTile);
            }

            // compute next direction: continuing straight is more likely than turning, turning back is unlikely but possible.
            previousDirection = currentDirection;
            currentDirection = EOrientation.getWeightedRandom(previousDirection);
//            currentDirection = EOrientation.getRandom();
        }


        // PLACING COLLECTABLES -------------------------------------------------------------------------------
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
            final int index = random.nextInt(turns.size());
            Tile tile = turns.get(index);
            new_item_x = tile.getPos_x();
            new_item_y = tile.getPos_y();
            items[new_item_x][new_item_y] = new CoinItem(new_item_x, new_item_y); // create new item
            // update lists and items count
            turns.remove(tile); // remove tiles that already have items.
            itemsPath.remove(tile); // remove tiles that already have items.
            placed_items++;
        }

        // place items randomly on the path
        while (placed_items < n_items && itemsPath.size() > 0) {
            // pick random tile on the path.
            final int index = random.nextInt(itemsPath.size());
            Tile tile = itemsPath.get(index);
            new_item_y = tile.getPos_y();
            new_item_x = tile.getPos_x();
            items[new_item_x][new_item_y] = new CoinItem(new_item_x, new_item_y); // create new item
            // update list and items count
            itemsPath.remove(tile); // remove tiles that already have items.
            placed_items++;
        }


        // WATER -----------------------------------------------------------------------------------------------
        // choose random position;
        final int water_start_x = random.nextInt(dim_x);
        final int water_start_y = random.nextInt(dim_y);
        Tile water_current_tile = grid[water_start_x][water_start_y];
        Tile water_previous_tile = water_current_tile;

        EOrientation water_current_direction = EOrientation.getRandom();

        // start walking
        for (int step = 0; step < water_n_steps; step++) {
            final int x = water_current_tile.getPos_x();
            final int y = water_current_tile.getPos_y();

            // if part of the path, replace tile with a bridge, otherwise replace it with water tile
            if (path.contains(water_current_tile)) {
                final int z = grid[x][y].getPos_z();
                grid[x][y] = new BridgeTile(x, y, z);
            } else {
                grid[x][y] = new WaterTile(x, y, 0);
            }

            // get next water tile, if the requested tile is not valid (out of the board) recompute direction and retry
            int count = 0;
            while(true) {
                try {
                    water_current_tile = getNextTileFromPositionAndDirection(grid, water_previous_tile.getPos_x(), water_previous_tile.getPos_y(), water_current_direction);
                    break;  // break out of loop on success
                } catch (IndexOutOfBoundsException e) {
                    // recompute direction and retry, max 100 tries
                    if (++count == 100) throw e;
                    water_current_direction = EOrientation.getRandom();
                }
            }
            // update direction
            water_current_direction = EOrientation.getWeightedRandom(water_current_direction);
        }


        // TILE ELEVATION -------------------------------------------------------------------------------------
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

        // Converts the matrix of Tiles and Items to their respective Lists.
        //  Assumes 'dim_x' and 'dim_y' are already correctly set.
        List<Tile> tile_list = new ArrayList<>(List.of());
        for(Tile[] row : grid)
            Collections.addAll(tile_list, row);
        this.grid = tile_list;
        List<Item> item_list = new ArrayList<>(List.of());
        for(Item[] row : items)
            item_list.addAll(Arrays.asList(row));
        this.items = item_list;
    }


    /**
     * Returns a Tile from a given position.
     *  Returns null if a Tile with the given coordinates does not exist.
     * @param x the x position.
     * @param y the y position.
     * @return the tile in the given position.
     * @throws IllegalArgumentException it the coordinates are out of bounds.
     */
    public Tile getTileAt(final int x, final int y) throws IndexOutOfBoundsException{
        if(x < 0 || y < 0 || x>=dim_x || y>=dim_y)
            throw new IndexOutOfBoundsException("Invalid coordinates");
        System.out.println("getTileAt("+x+","+y+")");
        for(Tile tile : grid) {
            System.out.println("Tile: " + tile.getPos_x() + " " + tile.getPos_y());
            if(tile.getPos_x() == x && tile.getPos_y() == y)
                return tile;
        }
        return null;
    }
    /**
     * Returns an Item from a given position.
     *  Returns null if an Item with the given coordinates does not exist.
     * @param x the x position.
     * @param y the y position.
     * @return the item in the given position.
     * @throws IllegalArgumentException it the coordinates are out of bounds.
     */
    public Item getItemAt(final int x, final int y) throws IndexOutOfBoundsException{
        if(x < 0 || y < 0 || x>=dim_x || y>=dim_y)
            throw new IndexOutOfBoundsException("Invalid coordinates");
        for(Item item : items) {
            if(item.getPos_x() == x && item.getPos_y() == y)
                return item;
        }
        return null;
    }

    /**
     * Returns the next Tile given the coordinates and the moving direction.
     *  Returns null if the Tile in the given position does not exist.
     * @param x the given x position.
     * @param y the given y position.
     * @param direction the given direction.
     * @return the tile we will step on.
     * @throws IndexOutOfBoundsException if the new computed position is outside the board.
     */
    public Tile getNextTileFromPositionAndDirection(final int x, final int y, EOrientation direction) throws IndexOutOfBoundsException {
        int new_x = x + direction.getDelta_x();
        int new_y = y + direction.getDelta_y();
        return getTileAt(new_x, new_y);
    }

    /**
     * Returns the next Tile given the coordinates and the moving direction in a given grid of tiles.
     * This method is used only during the automatic generation of the board.
     * @param grid the 2d array of Tiles.
     * @param x the given x position.
     * @param y the given y position.
     * @param direction the given direction.
     * @return the tile we will step on.
     * @throws IndexOutOfBoundsException if the new computed position is outside the board.
     */
    private Tile getNextTileFromPositionAndDirection(final Tile[][] grid, final int x, final int y, EOrientation direction) throws IndexOutOfBoundsException {
        int new_x = x + direction.getDelta_x();
        int new_y = y + direction.getDelta_y();
        return grid[new_x][new_y];
    }

    public boolean canStep(final int x, final int y, EOrientation direction) {
        // FIXME
        return true;
    }


    public BoardDTO toBoardDTO() {
        return new BoardDTO(this);
    }

    // Getters and setters below

    public int getDim_x(){
        return dim_x;
    }

    public int getDim_y(){
        return dim_y;
    }

    public List<Tile> getGrid(){
        return grid;
    }

    public List<Item> getItems(){
        return items;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getCoinsNumber() {
        return n_coins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return dim_x == board.dim_x && dim_y == board.dim_y && difficulty == board.difficulty && n_coins == board.n_coins && grid.equals(board.grid) && items.equals(board.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dim_x, dim_y, grid, items, difficulty, n_coins);
    }
}
