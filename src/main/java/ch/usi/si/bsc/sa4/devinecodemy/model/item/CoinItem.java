package ch.usi.si.bsc.sa4.devinecodemy.model.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Coin item, to be collected.
 */
public class CoinItem extends Item {

    /**
     * Construct for Coin items.
     * @param posX the x position of the coin.
     * @param posY the y position of the coin.
     */
    @JsonCreator
    public CoinItem(@JsonProperty("posX") int posX,
                    @JsonProperty("posY") int posY) {
        super(EItem.COIN, posX, posY);
    }
}
