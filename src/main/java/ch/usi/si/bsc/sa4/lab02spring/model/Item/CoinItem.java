package ch.usi.si.bsc.sa4.lab02spring.model.Item;

/**
 * A tile made of grass. You can walk on it.
 */
public class CoinItem extends Item {

    /**
     * Construct for Coin items.
     * @param pos_x the x position of the coin.
     * @param pos_y the y position of the coin.
     */
    public CoinItem(int pos_x, int pos_y) {
        super(pos_x, pos_y);
    }
}
