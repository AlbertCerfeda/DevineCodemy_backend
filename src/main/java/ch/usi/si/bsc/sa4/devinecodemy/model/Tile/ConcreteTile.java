package ch.usi.si.bsc.sa4.devinecodemy.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made of concrete. You can walk on it.
 */
public class ConcreteTile extends Tile {

    /**
     * Construct for Concrete tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public ConcreteTile(@JsonProperty("posX") int posX,
                        @JsonProperty("posY") int posY,
                        @JsonProperty("posZ") int posZ) {
        super(ETile.CONCRETE, posX, posY, posZ, true);
    }
}