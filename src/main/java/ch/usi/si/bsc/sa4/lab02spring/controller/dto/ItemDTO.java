package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;

public class ItemDTO {
    
    private int pos_x;
    private int pos_y;
    
    public ItemDTO(Item item) {
        pos_x = item.getPos_x();
        pos_y = item.getPos_y();
    }
}
