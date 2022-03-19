package ch.usi.si.bsc.sa4.lab02spring.model.Item;

import ch.usi.si.bsc.sa4.lab02spring.model.Item.Item;

/**
 * A tile made of grass. You can walk on it.
 */
public class CoinItem extends Item {

    /**
     * Construct for Grass tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     */
    public CoinItem(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }
}
