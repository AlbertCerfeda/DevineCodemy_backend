package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.Item.Item;

public class ItemDTO {
    /** 'type' is needed by the client for him to distinguish the different type of Items.  */
    final String type;
    
    private final int posX;
    private final int posY;
    
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
