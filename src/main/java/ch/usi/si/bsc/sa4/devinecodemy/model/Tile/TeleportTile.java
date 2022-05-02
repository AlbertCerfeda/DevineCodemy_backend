package ch.usi.si.bsc.sa4.devinecodemy.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile where the pleayer can teleport to another tile
 */
public class TeleportTile extends Tile {

    /**
     * Construct for Teleport tiles.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     */
    @JsonCreator
    public TeleportTile(@JsonProperty("pos_x") int pos_x,
                     @JsonProperty("pos_y") int pos_y,
                     @JsonProperty("pos_z") int pos_z) {
        super(ETile.TELEPORT, pos_x, pos_y, pos_z, true);
    }
}