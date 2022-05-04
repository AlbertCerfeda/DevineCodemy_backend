package ch.usi.si.bsc.sa4.devinecodemy.model.Tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile where the pleayer can teleport to another tile
 */
public class TeleportTile extends Tile {

    /**
     * Construct for Teleport tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public TeleportTile(@JsonProperty("posX") int posX,
                     @JsonProperty("posY") int posY,
                     @JsonProperty("posZ") int posZ) {
        super(ETile.TELEPORT, posX, posY, posZ, true);
    }
}
