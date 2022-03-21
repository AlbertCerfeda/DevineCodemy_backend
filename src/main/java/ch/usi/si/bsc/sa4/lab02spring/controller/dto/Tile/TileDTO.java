package ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile;

import ch.usi.si.bsc.sa4.lab02spring.model.Tile.ETile;
import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;

public class TileDTO {
    // Field 'type' is needed by the client for him to distinguish the different type of tiles
    private String type;
    
    private int pos_x;
    private int pos_y;
    private int pos_z;
    
    
    public TileDTO(Tile tile) {
        type = tile.getType().name();
        pos_x = tile.getPos_x();
        pos_y = tile.getPos_y();
        pos_z = tile.getPos_z();
    }
}
