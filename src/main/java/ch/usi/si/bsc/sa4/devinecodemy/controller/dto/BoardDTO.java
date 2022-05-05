package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.Item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.Tile.Tile;

public class BoardDTO {
    private final int dimX;
    private final int dimY;
    private List<TileDTO> grid;
    private List<ItemDTO> items;

    
    
    public BoardDTO(Board board) {
        dimX = board.getDimX();
        dimY = board.getDimY();
        
        // Converts the board made of Tile to TileDTOs
        grid = board.getGrid().stream().map(Tile::toTileDTO).collect(Collectors.toList());
        
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
