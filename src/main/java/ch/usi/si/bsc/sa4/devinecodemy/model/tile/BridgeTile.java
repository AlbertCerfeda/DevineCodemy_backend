package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A bridge allows the character to walk on water.
 */
public class BridgeTile extends Tile {

    /**
     * Constructor for BridgeTile object.
     * @param posX the x position of the bridge.
     * @param posY the y position of the bridge.
     * @param posZ the z position of the bridge.
     */
    @JsonCreator
    public BridgeTile(@JsonProperty("posX") int posX,
                      @JsonProperty("posY") int posY,
                      @JsonProperty("posZ") int posZ) {
        super(ETile.BRIDGE, posX, posY, posZ, true);
    }
}
