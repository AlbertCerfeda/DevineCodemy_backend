package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The TeleportTileDTO class represents the Teleport tile
 * to send to the user.
 */
public class TeleportTileDTO extends TileDTO {

    private boolean active;

    @JsonCreator
    public TeleportTileDTO(@JsonProperty("active") boolean active,
                           @JsonProperty("posX") int posX,
                           @JsonProperty("posY") int posY,
                           @JsonProperty("posZ") int posZ,
                           @JsonProperty("type") String type) {
        super(posX,posY,posZ,type);
        this.active = active;
    }

    public TeleportTileDTO(TeleportTile teleportTile) {
        super(teleportTile.getPosX(),teleportTile.getPosY(),teleportTile.getPosZ(),teleportTile.getType().toString());
        this.active = teleportTile.isActive();
    }

    public boolean isActive() {
        return active;
    }
}
