package ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile;

import ch.usi.si.bsc.sa4.lab02spring.model.Tile.Tile;

/**
 * The state of a Tile object to be sent to the client.
 */
public class TileDTO {
    // Field 'type' is needed by the client for him to distinguish the different type of tiles
    private String type;
    
    private int pos_x;
    private int pos_y;
    private int pos_z;
    
    
    public TileDTO(Tile tile) {
        type  = tile.getType().name();
        pos_x = tile.getPos_x();
        pos_y = tile.getPos_y();
        pos_z = tile.getPos_z();
    }
    
    
    
    // Getters and setters
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type=type;
    }
    
    public int getPos_x(){
        return pos_x;
    }
    
    public void setPos_x(int pos_x){
        this.pos_x=pos_x;
    }
    
    public int getPos_y(){
        return pos_y;
    }
    
    public void setPos_y(int pos_y){
        this.pos_y=pos_y;
    }
    
    public int getPos_z(){
        return pos_z;
    }
    
    public void setPos_z(int pos_z){
        this.pos_z=pos_z;
    }
}
