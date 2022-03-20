package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * A tile made of concrete. You can walk on it.
 */
public class ConcreteTile extends Tile {

    /**
     * Construct for Concrete tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    public ConcreteTile(int pos_x, int pos_y, int pos_z) {
        super(pos_x, pos_y, pos_z, true);
    }
}