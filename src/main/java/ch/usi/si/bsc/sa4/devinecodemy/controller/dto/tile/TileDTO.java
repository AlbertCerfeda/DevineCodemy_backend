package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Encapsulates a Tile state to be sent to the client.
 */
public class TileDTO {
    /** Field 'type' is needed by the client
     * to distinguish the different type of tiles */
    private final String type;
    
    private final int posX;
    private final int posY;
    private final int posZ;


    /**
     * Constructs a new TileDTO object with the given values.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public TileDTO(@JsonProperty("posX") int posX,
                   @JsonProperty("posY") int posY,
                   @JsonProperty("posZ") int posZ,
                   @JsonProperty("type") String type) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.type = type;
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
