package ch.usi.si.bsc.sa4.lab02spring.model.Item;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.ItemDTO;

/**
 * This class represents the general structure of a tile.
 */
public abstract class Item {
    protected EItem type = EItem.PLACEHOLDER;
    
    // position
    protected final int pos_x;
    protected final int pos_y;


    /**
     * Constructor for abstract class Tile.
     * @param type the EItem enum type.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     */
    public Item(EItem type, final int pos_x, final int pos_y) {
        this.type = type;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    /**
     * To get x position of the tile.
     * @return the x position of the tile.
     */
    public int getPos_x() {
        return pos_x;
    }

    /**
     * To get y position of the tile.
     * @return the y position of the tile.
     */
    public int getPos_y() {
        return pos_y;
    }
    
    public EItem getType(){
        return type;
    }
    
    public ItemDTO toItemDTO() { return new ItemDTO(this); }
    
    
}
