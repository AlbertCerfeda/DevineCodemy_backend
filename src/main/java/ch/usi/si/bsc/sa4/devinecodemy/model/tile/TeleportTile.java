package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TeleportTileDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A tile where the player can teleport to another teleport tile.
 */
public class TeleportTile extends Tile {

    private boolean active;
    private final int targetX;
    private final int targetY;
    private final int targetZ;

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
                        @JsonProperty("targetZ") final int targetZ,
                        @JsonProperty("coinsToActivate") int coinsToActivate) {
        super(ETile.TELEPORT, posX, posY, posZ, true);
        this.active = active;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
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

    public int getTargetZ(){
        return targetZ;
    }

    public int getCoinsToActivate(){
        return coinsToActivate;
    }

    /**
     * Returns the DTO of the object.
     * @return the TeleportTileDTO of this object.
     */
    public TeleportTileDTO toTeleportTileDTO() {
        return new TeleportTileDTO(this);
    }
}
