package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The BoardDTO class represents the state of a Board to be used
 *  by a client.
 */
public class BoardDTO {
    private final int dimX;
    private final int dimY;
    private final List<TileDTO> grid;
    private final List<ItemDTO> items;

    /**
     * Constructs a new empty BoardDTO object with the given values.
     * @param dimX the x dimension of the board.
     * @param dimY the y dimension of the board.
     * @param grid the grid composing the board.
     * @param items the items in the board.
     */
    @JsonCreator
    public BoardDTO(@JsonProperty("dimX") int dimX,
                    @JsonProperty("dimY") int dimY,
                    @JsonProperty("grid") List<TileDTO> grid,
                    @JsonProperty("items") List<ItemDTO> items) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.grid = grid;
        this.items = items;
    }

    /**
     * Constructs a new BoardDTO object of the given Board.
     * @param board the board to build the DTO from.
     */
    public BoardDTO(Board board) {
        dimX = board.getDimX();
        dimY = board.getDimY();
        
        // Converts the board made of Tile to TileDTOs
        grid = board.getGrid().stream().map(tile ->
                (tile instanceof TeleportTile) ?
                        ((TeleportTile) tile).toTeleportTileDTO() :
                        tile.toTileDTO()
        ).collect(Collectors.toList());

        
        // Converts the grid of Items to ItemDTOs
        items = board.getItems().stream().map(Item::toItemDTO).collect(Collectors.toList());
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
