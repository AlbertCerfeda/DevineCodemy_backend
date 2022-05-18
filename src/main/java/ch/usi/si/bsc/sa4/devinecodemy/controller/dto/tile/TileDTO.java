package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;

/**
 * Encapsulates a Tile state to be sent to the client.
 */
public class TileDTO {
    /** Field 'type' is needed by the client
     * to distinguish the different type of tiles */
    private String type;
    
    private int posX;
    private int posY;
    private int posZ;

    /**
     * Constructs a new TileDTO object.
     */
    public TileDTO() {
    }

    /**
     * Constructs a TileDTO object of the given tile.
     * @param tile the tile to be matched.
     */
    public TileDTO(Tile tile) {
        type = tile.getType().name();
        posX = tile.getPosX();
        posY = tile.getPosY();
        posZ = tile.getPosZ();
    }
    
    
    // Getters and setters
    
    public String getType(){
        return type;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public int getPosZ(){
        return posZ;
    }
}
