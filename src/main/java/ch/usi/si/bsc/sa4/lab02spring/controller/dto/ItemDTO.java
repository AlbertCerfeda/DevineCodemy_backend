package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;

public class ItemDTO {
    // Field 'type' is needed by the client for him to distinguish the different type of Items
    String type;
    
    private int pos_x;
    private int pos_y;
    
    public ItemDTO(Item item) {
        type = item.getType().name();
        pos_x = item.getPos_x();
        pos_y = item.getPos_y();
    }
}
