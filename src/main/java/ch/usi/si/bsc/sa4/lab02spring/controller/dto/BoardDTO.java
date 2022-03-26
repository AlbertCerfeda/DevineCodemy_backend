package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile.TileDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Board;

public class BoardDTO {
    private int dim_x;
    private int dim_y;
    private TileDTO[][] grid;
    private ItemDTO[][] items;

    
    
    public BoardDTO(Board board) {
        dim_x = board.getDim_x();
        dim_y = board.getDim_y();
        
        // Converts the board made of Tile to TileDTOs
        for (int x = 0; x < board.getGrid().length; x++) {
            for (int y = 0; y < board.getGrid()[x].length; y++) {
                grid[x][y] = board.getGrid()[x][y].toTileDTO();
            }
        }
        
        // Converts the grid of Items to ItemDTOs
        for (int x = 0; x < board.getItems().length; x++) {
            for (int y = 0; y < board.getItems()[x].length; y++) {
                items[x][y] = board.getItems()[x][y].toItemDTO();
            }
        }
    }
}
