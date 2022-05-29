package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;

/**
 * The TeleportTileDTO class represents the Teleport tile
 * to send to the user.
 */
public class TeleportTileDTO extends TileDTO {

    private final boolean active;
    private final int targetX;
    private final int targetY;
    private final int targetZ;

    /**
     * Constructs a new TeleportTileDTO object of the given TeleportTile.
     * @param teleportTile the teleport tile to be mapped.
     */
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
