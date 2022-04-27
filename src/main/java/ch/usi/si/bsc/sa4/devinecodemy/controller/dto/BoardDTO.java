package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.Tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.Item.Item;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.Tile.Tile;

public class BoardDTO {
    private int dim_x;
    private int dim_y;
    private List<TileDTO> grid;
    private List<ItemDTO> items;

    
    
    public BoardDTO(Board board) {
        dim_x = board.getDim_x();
        dim_y = board.getDim_y();
        
        // Converts the board made of Tile to TileDTOs
        grid = board.getGrid().stream().map(Tile::toTileDTO).collect(Collectors.toList());
        
        // Converts the grid of Items to ItemDTOs
        items = board.getItems().stream().map(Item::toItemDTO).collect(Collectors.toList());
    }
}
