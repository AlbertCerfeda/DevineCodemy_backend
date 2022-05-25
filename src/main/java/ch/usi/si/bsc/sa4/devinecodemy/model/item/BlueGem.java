package ch.usi.si.bsc.sa4.devinecodemy.model.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Blue gem item, to be collected.
 */
public class BlueGem extends Item {

    /**
     * Construct for BlueGem items.
     * @param posX the x position of the gem.
     * @param posY the y position of the gem.
     */
    @JsonCreator
    public BlueGem(@JsonProperty("posX") int posX,
                   @JsonProperty("posY") int posY) {
        super(EItem.BLUEGEM, posX, posY);
    }
}
