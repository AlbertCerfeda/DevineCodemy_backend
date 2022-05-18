package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;

/**
 * The ItemDTO class represents the Item to be consumed.
 * by a player.
 */
public class ItemDTO {
    /** 'type' is needed by the client for him
     * to distinguish the different type of Items.  */
    private String type;
    
    private int posX;
    private int posY;

    /**
     * Constructs a new empty ItemDTO object.
     */
    public ItemDTO() {
    }

    /**
     * Constructs a new ItemDTO object of the given item.
     * @param item the Item to build the DTO from.
     */
    public ItemDTO(Item item) {
        type = item.getType().name();
        posX = item.getPosX();
        posY = item.getPosY();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getType() {
        return type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
