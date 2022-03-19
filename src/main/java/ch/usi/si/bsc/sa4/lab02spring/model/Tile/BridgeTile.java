package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * A bridge allow the character to walk on water.
 */
public class BridgeTile extends WalkableTile{

    /**
     * Construct for BridgeTile object. This is a fixed bridge, it can only be positioned on top of the water
     * @param pos_x the x position of the bridge.
     * @param pos_y the y position of the bridge.
     * @param pos_z the z position of the bridge.
     */
    public BridgeTile(int pos_x, int pos_y, int pos_z) {
        super(pos_x, pos_y, pos_z);
    }
}
