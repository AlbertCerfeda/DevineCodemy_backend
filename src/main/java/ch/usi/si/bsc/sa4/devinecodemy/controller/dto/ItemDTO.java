package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.item.Item;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ItemDTO class represents the Item to be consumed.
 * by a player.
 */
public class ItemDTO {
    /** 'type' is needed by the client for him
     * to distinguish the different type of Items.  */
    private final String type;
    
    private final int posX;
    private final int posY;


    /**
     * Constructs a new ItemDTO object with the given values.
     * @param posX the x position of the Item.
     * @param posY the y position of the Item.
     */
    @JsonCreator
    public ItemDTO(@JsonProperty("posX") int posX,
                   @JsonProperty("posY") int posY,
                   @JsonProperty("type") String type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
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
