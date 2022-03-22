package ch.usi.si.bsc.sa4.lab02spring.model;

import java.util.ArrayList;
import java.util.List;
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
     * To get a random direction depending on the given direction with the following weights:
     *  - 50% proceeding forward;
     *  - 10% going back;
     *  - 20% turn left;
     *  - 20% turn right.
     * @param orientation the given direction
     * @return a random direction weighted on the given direction.
     */
    public static EOrientation getWeightedRandom(EOrientation orientation) {
        // building the weighted array from which we pick a random orientation
        ArrayList<EOrientation> weighted_list = new ArrayList<>();
        add_n_times_to_list(weighted_list, orientation, 5);
        add_n_times_to_list(weighted_list, getOppositeDirection(orientation), 1);
        for (int i = 0; i < values().length; i++) {
            if (!weighted_list.contains(values()[i]))
                add_n_times_to_list(weighted_list, values()[i], 2);
        }
        // picking random orientation from the weighted list
        Random random = new Random();
        final int r = random.nextInt(weighted_list.size()-1);
        return weighted_list.get(r);
    }

    // helper method to add an orientation n times into a given list
    private static void add_n_times_to_list(List<EOrientation> list, EOrientation object, int n) {
        for (int i = 0; i <n; i++) {
            list.add(object);
        }
    }

    /**
     * Returns the opposite direction to the given one.
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
    
    /**
     * Returns the new direction by turning the given direction to the left.
     * @param orientation the given direction.
     * @return the direction turned to the left.
     */
    public static EOrientation turnLeft(EOrientation orientation) {
        switch (orientation) {
            case UP:
                return LEFT;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case RIGHT:
                return UP;
        }
        // else invalid orientation, return UP as default.
        return EOrientation.UP;
    }
    /**
     * Returns the new direction by turning the given direction to the right.
     * @param orientation the given direction.
     * @return the direction turned to the right.
     */
    public static EOrientation turnRight(EOrientation orientation) {
        switch (orientation) {
            case UP:
                return RIGHT;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case RIGHT:
                return DOWN;
        }
        // else invalid orientation, return UP as default.
        return EOrientation.UP;
    }
}
