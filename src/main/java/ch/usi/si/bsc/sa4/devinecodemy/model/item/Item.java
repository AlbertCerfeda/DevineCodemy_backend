package ch.usi.si.bsc.sa4.devinecodemy.model.item;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.ItemDTO;

import java.util.Objects;

/**
 * This class represents the general structure of a tile.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CoinItem.class, name = "COIN"),
})
public abstract class Item {
    protected EItem type;
    
    // position
    protected final int posX;
    protected final int posY;

    /**
     * Constructor for abstract class Tile.
     * @param type the EItem enum type.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     */
    protected Item(EItem type, final int posX, final int posY) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * To get x position of the tile.
     * @return the x position of the tile.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * To get y position of the tile.
     * @return the y position of the tile.
     */
    public int getPosY() {
        return posY;
    }
    
    public EItem getType(){
        return type;
    }

    /**
     * Returns the ItemDTO of this object.
     * @return the ItemDTO of this object.
     */
    public ItemDTO toItemDTO() { return new ItemDTO(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        final Item item = (Item) o;
        return posX == item.posX && posY == item.posY && type.equals(item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, posX, posY);
    }
}
