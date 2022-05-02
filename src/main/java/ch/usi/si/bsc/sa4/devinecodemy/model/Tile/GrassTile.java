package ch.usi.si.bsc.sa4.devinecodemy.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile made of grass. You can walk on it.
 */
public class GrassTile extends Tile {

    /**
     * Construct for Grass tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    @JsonCreator
    public GrassTile(@JsonProperty("pos_x") int pos_x,
                     @JsonProperty("pos_y") int pos_y,
                     @JsonProperty("pos_z") int pos_z) {
        super(ETile.GRASS, pos_x, pos_y, pos_z, true);
    }
}