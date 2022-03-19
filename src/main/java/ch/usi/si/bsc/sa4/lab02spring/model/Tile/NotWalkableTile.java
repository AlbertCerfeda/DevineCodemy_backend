package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * A NotWalkableTile do NOT allow the player to walk on it.
 */
public class NotWalkableTile extends Tile {

    /**
     * Construct for NotWalkable tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    public NotWalkableTile(int pos_x, int pos_y, int pos_z) {
        super(pos_x, pos_y, pos_z, false);
    }
}
