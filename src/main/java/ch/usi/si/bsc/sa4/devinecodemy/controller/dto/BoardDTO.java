package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;

/**
 * The BoardDTO class represents the state of a Board to be used
 *  by a client.
 */
public class BoardDTO {
    private int dimX;
    private int dimY;
    private List<TileDTO> grid;
    private List<ItemDTO> items;

    /**
     * Constructs a new empty BoardDTO object.
     */
    public BoardDTO() {
    }

    /**
     * Constructs a new BoardDTO object of the given Board.
     * @param board the board to build the DTO from.
     */
    public BoardDTO(Board board) {
        dimX = board.getDimX();
        dimY = board.getDimY();
        
        // Converts the board made of Tile to TileDTOs
        grid = board.getGrid().stream().map(Tile::toTileDTO).collect(Collectors.toList());
        
        // Converts the grid of Items to ItemDTOs
        items = board.getItems().stream().map(Item::toItemDTO).collect(Collectors.toList());
    }

    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    public void setDimY(int dimY) {
        this.dimY = dimY;
    }

    public void setGrid(List<TileDTO> grid) {
        this.grid = grid;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public List<TileDTO> getGrid() {
        return grid;
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}
