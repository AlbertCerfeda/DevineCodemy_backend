package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile.TileDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This class represents the general structure of a tile.
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
})
public abstract class Tile {
    // Used by the TileDTO to indicate the type of the Tile.
    //  Subclasses of the Tile class are expected to set the value of the type field accordingly.
    protected ETile type = ETile.PLACEHOLDER;
    
    // Position
    protected final int pos_x;
    protected final int pos_y;
    protected int pos_z;
    //
    
    protected boolean is_walkable;

//    protected final String material;
//    protected final String shape;

    private boolean visited = false;

    /**
     * Constructor for abstract class Tile.
     * @param type the ENUM representing the type of the tile.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     * @param is_walkable true if the tile is walkable, false otherwise.
     */
    public Tile(ETile type, final int pos_x, final int pos_y, final int pos_z, final boolean is_walkable) {
        this.type = type;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.pos_z = pos_z;
        this.is_walkable = is_walkable;
    }

    /**
     * To know if this tile is walkable or not.
     * @return true if this tile is walkable, false otherwise.
     */
    public boolean is_walkable() {
        return is_walkable;
    }
    
    public void set_walkable(boolean is_walkable) {
        this.is_walkable = is_walkable;
    }
    
    /**
     * To get the height position of the tile.
     * @return the height position of the tile.
     */
    public int getPos_z() {
        return pos_z;
    }

    /**
     * To set the height position of the tile.
     */
    public void setPos_z(final int pos_z) {
        this.pos_z = pos_z;
    }

    /**
     * To get x position of the tile.
     * @return the x position of the tile.
     */
    public int getPos_x() {
        return pos_x;
    }

    /**
     * To get y position of the tile.
     * @return the y position of the tile.
     */
    public int getPos_y() {
        return pos_y;
    }

    /**
     * Returns the enum type of the Tile.
     * @return the enum type of the tile.
     */
    public ETile getType(){
        return type;
    }
    
    /**
     * To get if the tile has been already visited or not.
     * Useful when creating board.
     * @return true if visited, false otherwise.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * To set if the tile has been already visited or not.
     * Useful when creating board.
     * @param visited the value to set.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    
    public TileDTO toTileDTO() {
        return new TileDTO(this);
    }
}
