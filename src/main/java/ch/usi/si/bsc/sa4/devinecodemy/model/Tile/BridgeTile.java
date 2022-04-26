package ch.usi.si.bsc.sa4.devinecodemy.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonCreator
    public BridgeTile(@JsonProperty("pos_x") int pos_x,
                      @JsonProperty("pos_y") int pos_y,
                      @JsonProperty("pos_z") int pos_z) {
        super(ETile.BRIDGE, pos_x, pos_y, pos_z, true);
    }
}
