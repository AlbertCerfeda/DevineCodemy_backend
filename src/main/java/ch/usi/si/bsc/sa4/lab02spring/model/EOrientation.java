package ch.usi.si.bsc.sa4.lab02spring.model;

import java.util.Random;

/**
 * All the possible orientations.
 */
public enum EOrientation {
    UP(0,-1),
    DOWN(0,1),
    LEFT(-1, 0),
    RIGHT(1,0);

    private final int delta_x;
    private final int delta_y;

    /**
     * Private constructor for enum values.
     * Each value is associated with a delta_z and a delta_y which together mathematically represent the direction.
     * @param delta_x the x variation.
     * @param delta_y the y variation.
     */
    EOrientation (int delta_x, int delta_y) {
        this.delta_x = delta_x;
        this.delta_y = delta_y;
    }

    /**
     * Returns the x direction.
     * @return the x direction.
     */
    public int getDelta_x() {
        return delta_x;
    }

    /**
     * Returns the y direction.
     * @return the y direction.
     */
    public int getDelta_y() {
        return delta_y;
    }

    /**
     * To get a random direction.
     * @return a random direction.
     */
    public static EOrientation getRandom() {
        Random random = new Random();
        final int r = random.nextInt(values().length);
        return values()[r];
    }

    /**
     * To get the opposite direction to the given one.
     * @param orientation the given direction.
     * @return the opposite direction to the given direction.
     */
    public static EOrientation getOppositeDirection(EOrientation orientation) {
        switch (orientation) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        // else invalid orientation, return UP as default.
        return EOrientation.UP;
    }
}
