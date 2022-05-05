package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.Tile.Tile;

/**
 * Encapsulates a Tile state to be sent to the client.
 */
public class TileDTO {
    // Field 'type' is needed by the client for him to distinguish the different type of tiles
    private String type;
    
    private int posX;
    private int posY;
    private int posZ;
    
    
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
    
    public void setType(String type){
        this.type=type;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public int getPosZ(){
        return posZ;
    }
    
    public void setPosZ(int posZ){
        this.posZ = posZ;
    }
}
