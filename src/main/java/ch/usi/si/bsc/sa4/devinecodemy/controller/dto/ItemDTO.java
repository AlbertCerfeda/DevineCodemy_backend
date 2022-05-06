package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;

/**
 * The ItemDTO class represents the Item to be consumed
 * by a client.
 */
public class ItemDTO {
    /** 'type' is needed by the client for him
     * to distinguish the different type of Items.  */
    private final String type;
    
    private final int posX;
    private final int posY;

    /**
     * Constructs a new ItemDTO object of the given item.
     * @param item the Item to be matched.
     */
    public ItemDTO(Item item) {
        type = item.getType().name();
        posX = item.getPosX();
        posY = item.getPosY();
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
