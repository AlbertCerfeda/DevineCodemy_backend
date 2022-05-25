package ch.usi.si.bsc.sa4.devinecodemy.model.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Red Gem item, to be collected.
 */
public class RedGem extends Item {

    /**
     * Construct for Red Gem items.
     * @param posX the x position of the gem.
     * @param posY the y position of the gem.
     */
    @JsonCreator
    public RedGem(@JsonProperty("posX") int posX,
                  @JsonProperty("posY") int posY) {
        super(EItem.REDGEM, posX, posY);
    }
}
