package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import org.springframework.data.annotation.PersistenceConstructor;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;


/**
 * A Board representing a map.
 * Contains the terrain on which the player moves and the collectable items.
 */
public class Board {
    private int dimX;
    private int dimY;
    private final List<Tile> grid;
    private final List<Item> items;
    private final int nCoins;
    
    /**
     * Constructor for board objects.
     * @param grid the tiles of the board representing the terrain.
     * @param items the items present on the board.
     */
    @PersistenceConstructor
    public Board(@JsonProperty("grid") List<Tile> grid,
                 @JsonProperty("items") List<Item> items,
                 @JsonProperty("nCoins") int nCoins) {
        this.dimX = 0;
        this.dimY = 0;
        // Derives the dimensions of the board
        grid.forEach(t-> this.dimX = Math.max(this.dimX, t.getPosX() + 1));
        grid.forEach(t-> this.dimY = Math.max(this.dimY, t.getPosY() + 1));
        items.forEach(i-> this.dimX = Math.max(this.dimX, i.getPosX() + 1));
        items.forEach(i-> this.dimY = Math.max(this.dimY, i.getPosY() + 1));
        
        
        this.grid = grid;
        this.items = items;
        this.nCoins = nCoins;
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
        if(x < 0 || y < 0 || x>= dimX || y>= dimY)
            throw new IndexOutOfBoundsException("Invalid coordinates");
        for(Tile tile : grid) {
            if(tile.getPosX() == x && tile.getPosY() == y)
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
        if(x < 0 || y < 0 || x>= dimX || y>= dimY)
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
        final int newX = x + direction.getDeltaX();
        final int newY = y + direction.getDeltaY();
        return getTileAt(newX, newY);
    }

    public boolean canStep(final int x, final int y, EOrientation direction) {
        try {
            final Tile nextTile = getNextTileFromPositionAndDirection(x, y, direction);
            final int deltaZ = Math.abs(nextTile.getPosZ() - getTileAt(x,y).getPosZ());
            return nextTile.isWalkable() && deltaZ <= 1;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    
    public BoardDTO toBoardDTO() {
        return new BoardDTO(this);
    }
    
    // Getters and setters below
    
    public int getDimX(){
        return dimX;
    }
    
    public int getDimY(){
        return dimY;
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
        return nCoins;
    }

    /**
     * Returns a string representation of the board.
     * @return a string representing the board.
     */
    public String toString() {
        char[][] result = new char[dimX][dimY];
        for (Tile t : grid) {
            result[t.getPosX()][t.getPosY()] =
                    containsItemAt(t.getPosX(), t.getPosY()) ? '*' :
                    t.getType().toString().charAt(0);
        }
        final StringBuilder builder = new StringBuilder();
        for (char[] line : result) {
            builder.append(String.valueOf(line));
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        final Board board = (Board) o;
        return dimX == board.dimX && dimY == board.dimY && nCoins == board.nCoins && Objects.equals(grid, board.grid) && Objects.equals(items, board.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimX, dimY, grid, items, nCoins);
    }
}
