package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made Sky. You can NOT walk on it.
 */
public class NormalSkyTile extends Tile {

    /**
     * Construct for Water tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    @JsonCreator
    public NormalSkyTile(@JsonProperty("pos_x") int pos_x,
                     @JsonProperty("pos_y") int pos_y,
                     @JsonProperty("pos_z") int pos_z) {
        super(ETile.NORMALS, pos_x, pos_y, pos_z, false);
    }
}
