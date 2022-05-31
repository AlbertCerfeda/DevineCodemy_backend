package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The LeverTile class represents the Lever's Tile.
 */
public class LeverTile extends Tile{

    private final int teleportX;
    private final int teleportY;
    private final int teleportZ;
    private boolean active;
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
                     @JsonProperty("teleportY") int teleportY,
                     @JsonProperty("teleportZ") int teleportZ,
                     @JsonProperty("active") boolean active) {
        super(ETile.LEVER, posX, posY, posZ, true);
        this.teleportX = teleportX;
        this.teleportY = teleportY;
        this.teleportZ = teleportZ;
        this.active = active;
    }

    public int getTeleportX() {
        return teleportX;
    }

    public int getTeleportY() {
        return teleportY;
    }

    public int getTeleportZ() {
        return teleportZ;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean status) {
        this.active = status;
    }
}