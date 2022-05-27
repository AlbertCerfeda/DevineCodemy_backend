package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TeleportTileDTO class represents the Teleport tile
 * to send to the user.
 */
public class TeleportTileDTO extends TileDTO {

    private final boolean active;
    private final int targetX;
    private final int targetY;
    private final int targetZ;

    @JsonCreator
    public TeleportTileDTO(@JsonProperty("active") boolean active,
                           @JsonProperty("posX") int posX,
                           @JsonProperty("posY") int posY,
                           @JsonProperty("posZ") int posZ,
                           @JsonProperty("type") String type,
                           @JsonProperty("targetX") int targetX,
                           @JsonProperty("targetY") int targetY,
                           @JsonProperty("targetZ") int targetZ) {
        super(posX,posY,posZ,type);
        this.active = active;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public TeleportTileDTO(TeleportTile teleportTile) {
        super(teleportTile.getPosX(),teleportTile.getPosY(),teleportTile.getPosZ(),teleportTile.getType().toString());
        this.active = teleportTile.isActive();
        this.targetX = teleportTile.getTargetX();
        this.targetY = teleportTile.getTargetY();
        this.targetZ = teleportTile.getTargetZ();
    }

    public boolean isActive() {
        return active;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getTargetZ() {
        return targetZ;
    }
}
