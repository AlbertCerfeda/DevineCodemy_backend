package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made Sky. You can NOT walk on it.
 */
public class NormalSkyTile extends Tile {

    /**
     * Construct for Water tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public NormalSkyTile(@JsonProperty("posX") int posX,
                     @JsonProperty("posY") int posY,
                     @JsonProperty("posZ") int posZ) {
        super(ETile.NORMALS, posX, posY, posZ, false);
    }
}
