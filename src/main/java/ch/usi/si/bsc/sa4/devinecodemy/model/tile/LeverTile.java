package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A lever allows the character to interact with a teleport portal.
 */
public class LeverTile extends Tile{

    private final int teleportX;
    private final int teleportY;
    /**
     * Constructor for LeverTile object.
     * @param posX the x position of the lever.
     * @param posY the y position of the lever.
     * @param posZ the z position of the lever.
     */

    @JsonCreator
    public LeverTile(@JsonProperty("posX") int posX,
                     @JsonProperty("posY") int posY,
                     @JsonProperty("posZ") int posZ,
                     @JsonProperty("teleportX") int teleportX,
                     @JsonProperty("teleportY") int teleportY) {
        super(ETile.LEVER, posX, posY, posZ, true);
        this.teleportX = teleportX;
        this.teleportY = teleportY;
    }

    public int getTeleportX() {
        return teleportX;
    }

    public int getTeleportY() {
        return teleportY;
    }
}
