package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile where the player can teleport to another teleport tile.
 */
public class TeleportTile extends Tile {

    private boolean active;
    private final int targetX;
    private final int targetY;

    private final int coinsToActivate;

    /**
     * Construct for Teleport tiles.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     */
    @JsonCreator
    public TeleportTile(@JsonProperty("posX") int posX,
                        @JsonProperty("posY") int posY,
                        @JsonProperty("posZ") int posZ,
                        @JsonProperty("active") boolean active,
                        @JsonProperty("targetX") final int targetX,
                        @JsonProperty("targetY") final int targetY,
                        @JsonProperty("coinsToActivate") int coinsToActivate) {
        super(ETile.TELEPORT, posX, posY, posZ, true);
        this.active = active;
        this.targetX = targetX;
        this.targetY = targetY;
        this.coinsToActivate = coinsToActivate;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean status){
        this.active = status;
    }

    public int getTargetX(){
        return targetX;
    }

    public int getTargetY(){
        return targetY;
    }

    public int getCoinsToActivate(){
        return coinsToActivate;
    }
}
