package ch.usi.si.bsc.sa4.devinecodemy.model.tile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;

import java.util.Objects;

/**
 * The Tile class represents the general structure of a tile.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WaterTile.class, name = "WATER"),
        @JsonSubTypes.Type(value = SandTile.class, name = "SAND"),
        @JsonSubTypes.Type(value = GrassTile.class, name = "GRASS"),
        @JsonSubTypes.Type(value = ConcreteTile.class, name = "ROCK"),
        @JsonSubTypes.Type(value = BridgeTile.class, name = "BRIDGE"),
        @JsonSubTypes.Type(value = GreyCloudTile.class, name = "CLOUDG"),
        @JsonSubTypes.Type(value = BlackCloudTile.class, name = "CLOUDB"),
        @JsonSubTypes.Type(value = ConcreteTile.class, name = "CONCRETE"),
        @JsonSubTypes.Type(value = TeleportTile.class, name = "TELEPORT"),
        @JsonSubTypes.Type(value = LavaTile.class, name = "LAVA"),
        @JsonSubTypes.Type(value = ObsidianTile.class, name = "OBSIDIAN"),
        @JsonSubTypes.Type(value = LeverTile.class, name = "LEVER"),

})
public abstract class Tile {
    /** Used by the TileDTO to indicate the type of the Tile.
     *  Subclasses of the Tile class are expected
     * to set the value of the type field accordingly. */
    protected ETile type;

    /** Position of the Tile object */
    protected final int posX;
    protected final int posY;
    protected int posZ;

    protected boolean isWalkable;


    /**
     * Constructor for abstract class Tile.
     * @param type the ENUM representing the type of the tile.
     * @param posX the x position of the tile.
     * @param posY the y position of the tile.
     * @param posZ the z position of the tile.
     * @param isWalkable true if the tile is walkable, false otherwise.
     */
    protected Tile(ETile type, final int posX, final int posY, final int posZ, final boolean isWalkable) {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.isWalkable = isWalkable;
    }

    /**
     * To know if this tile is walkable or not.
     * @return true if this tile is walkable, false otherwise.
     */
    public boolean isWalkable() {
        return isWalkable;
    }

    /**
     * Sets the isWalkable field to the new value.
     * @param isWalkable new value indicating whether
     *                   the tile is walkable or not.
     */
    public void setWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }

    /**
     * To get the height position of the tile.
     * @return the height position of the tile.
     */
    public int getPosZ() {
        return posZ;
    }

    /**
     * To get x position of the tile.
     * @return the x position of the tile.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * To get y position of the tile.
     * @return the y position of the tile.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Returns the enum type of the Tile.
     * @return the enum type of the tile.
     */
    public ETile getType(){
        return type;
    }

    /**
     * Checks if the tile is a teleport tile.
     * @return true if this tile is a teleport, otherwise false.
     */
    public boolean isTeleport(){
        return type == ETile.TELEPORT;
    }

    /**
     * To get if the tile has been already visited or not.
     * Useful when creating board.
     * @return true if visited, false otherwise.
     */
    public TileDTO toTileDTO() {
        return new TileDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        final Tile tile = (Tile) o;
        return posX == tile.posX && posY == tile.posY && posZ == tile.posZ && isWalkable == tile.isWalkable && type == tile.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, posX, posY, posZ, isWalkable);
    }
}
