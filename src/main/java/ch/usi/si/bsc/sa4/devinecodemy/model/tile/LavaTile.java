package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made of Lava. You can NOT walk on it.
 */
public class LavaTile extends Tile {

    /**
     * Constructor for Lava tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public LavaTile(@JsonProperty("posX") int posX,
                     @JsonProperty("posY") int posY,
                     @JsonProperty("posZ") int posZ) {
        super(ETile.LAVA, posX, posY, posZ, false);
    }
}

