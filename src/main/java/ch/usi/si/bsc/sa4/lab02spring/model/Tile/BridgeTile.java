package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * A bridge allows the character to walk on water.
 */
public class BridgeTile extends Tile {
    
    /**
     * Construct for BridgeTile object.
     * @param pos_x the x position of the bridge.
     * @param pos_y the y position of the bridge.
     * @param pos_z the z position of the bridge.
     */
    public BridgeTile(int pos_x, int pos_y, int pos_z) {
        super(ETile.BRIDGE, pos_x, pos_y, pos_z, true);
    }
}
