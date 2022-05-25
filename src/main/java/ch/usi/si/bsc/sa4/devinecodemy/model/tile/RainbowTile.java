package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A rainbow is a tile in paradise.
 */
public class RainbowTile extends Tile {

    /**
     * Constructor for RainbowTile object.
     * @param posX the x position of the rainbow.
     * @param posY the y position of the rainbow.
     * @param posZ the z position of the rainbow.
     */
    @JsonCreator
    public RainbowTile(@JsonProperty("posX") int posX,
                      @JsonProperty("posY") int posY,
                      @JsonProperty("posZ") int posZ) {
        super(ETile.RAINBOW, posX, posY, posZ, true);
    }
}
