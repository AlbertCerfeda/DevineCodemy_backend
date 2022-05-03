package ch.usi.si.bsc.sa4.devinecodemy.model.Item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made of grass. You can walk on it.
 */
public class CoinItem extends Item {

    /**
     * Construct for Coin items.
     * @param pos_x the x position of the coin.
     * @param pos_y the y position of the coin.
     */
    @JsonCreator
    public CoinItem(@JsonProperty("pos_x") int pos_x,
                    @JsonProperty("pos_y") int pos_y) {
        super(EItem.COIN, pos_x, pos_y);
    }
}
