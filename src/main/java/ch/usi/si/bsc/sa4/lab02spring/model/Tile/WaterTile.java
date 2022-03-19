package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * A tile made of Water. You can NOT walk on it.
 */
public class WaterTile extends NotWalkableTile{

    /**
     * Construct for Water tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    public WaterTile(int pos_x, int pos_y, int pos_z) {
        super(pos_x, pos_y, pos_z);
    }
}
