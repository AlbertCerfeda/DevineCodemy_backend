package ch.usi.si.bsc.sa4.devinecodemy.model.Level;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import org.springframework.data.annotation.PersistenceConstructor;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.Item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.Tile.Tile;


/**
 * A Board representing a map.
 * Contains the terrain on which the player moves and the collectable items.
 */
public class Board {
    private int dim_x;
    private int dim_y;
    private List<Tile> grid;
    private List<Item> items;
    private int n_coins;
    
    /**
     * Constructor for board objects.
     * @param grid the tiles of the board representing the terrain.
     * @param items the items present on the board.
     */
    @PersistenceConstructor
    public Board(@JsonProperty("grid") List<Tile> grid,
                 @JsonProperty("items") List<Item> items,
                 @JsonProperty("n_coins") int n_coins) {
        this.dim_x = 0;
        this.dim_y = 0;
        // Derives the dimensions of the board
        grid.forEach((t)-> this.dim_x = Math.max(this.dim_x, t.getPos_x() + 1));
        grid.forEach((t)-> this.dim_y = Math.max(this.dim_y, t.getPos_y() + 1));
        items.forEach((i)-> this.dim_x = Math.max(this.dim_x, i.getPosX() + 1));
        items.forEach((i)-> this.dim_y = Math.max(this.dim_y, i.getPosY() + 1));
        
        
        this.grid = grid;
        this.items = items;
        this.n_coins = n_coins;
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
        //System.out.println("getTileAt("+x+","+y+")");
        for(Tile tile : grid) {
            //System.out.println("Tile: " + tile.getPos_x() + " " + tile.getPos_y());
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
            if(item.getPosX() == x && item.getPosY() == y)
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

    public boolean canStep(final int x, final int y, EOrientation direction) {
        try {
            Tile nextTile = getNextTileFromPositionAndDirection(x, y, direction);
            int delta_z = Math.abs(nextTile.getPos_z() - getTileAt(x,y).getPos_z());
            return nextTile.is_walkable() && delta_z <= 1;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
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

    public boolean containsItemAt(final int x, final int y) {
        try {
            return getItemAt(x, y) != null;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }


    public int getCoinsNumber() {
        return n_coins;
    }

    /**
     * Returns a string representation of the board.
     * @return a string representing the board.
     */
    public String toString() {
        char[][] result = new char[dim_x][dim_y];
        for (Tile t : grid) {
            result[t.getPos_x()][t.getPos_y()] =
                    containsItemAt(t.getPos_x(), t.getPos_y()) ? '*' :
                    t.getType().toString().charAt(0);
        }
        String res = "";
        for (char[] line : result)
            res+=String.valueOf(line)+"\n";
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;
        return dim_x == board.dim_x && dim_y == board.dim_y && n_coins == board.n_coins && Objects.equals(grid, board.grid) && Objects.equals(items, board.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dim_x, dim_y, grid, items, n_coins);
    }
}
