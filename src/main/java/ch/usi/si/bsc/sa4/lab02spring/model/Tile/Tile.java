package ch.usi.si.bsc.sa4.lab02spring.model.Tile;

/**
 * This class represents the general structure of a tile.
 *
 */
public abstract class Tile {

    // position
    protected int pos_x;
    protected int pos_y;
    protected int pos_z;

    //
    protected final boolean is_walkable;

//    protected final String material;
//    protected final String shape;

    /**
     * Constructor for abstract class Tile.
     * @param pos_x the x position of the tile.
     * @param pos_y the y position of the tile.
     * @param pos_z the z position of the tile.
     * @param is_walkable true if the tile is walkable, false otherwise.
     */
    public Tile(int pos_x, int pos_y, int pos_z, boolean is_walkable) {
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
    public void setPos_z(int pos_z) {
        this.pos_z = pos_z;
    }

}
