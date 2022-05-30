package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made of sand. You can walk on it.
 */
public class SandTile extends Tile {

    /**
     * Construct for Sand tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public SandTile(@JsonProperty("posX") int posX,
                    @JsonProperty("posY") int posY,
                    @JsonProperty("posZ") int posZ) {
        super(ETile.SAND, posX, posY, posZ, true);
    }
}
