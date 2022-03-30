package ch.usi.si.bsc.sa4.lab02spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * All the possible orientations.
 */
public enum EOrientation {
    UP(0,-1) {
        @Override
        public EOrientation getOpposite() { return DOWN; }
        @Override
        public EOrientation turnLeft()    { return LEFT; }
        @Override
        public EOrientation turnRight()   { return RIGHT; }
    },
    DOWN(0,1) {
        @Override
        public EOrientation getOpposite() { return UP; }
        @Override
        public EOrientation turnLeft()    { return RIGHT; }
        @Override
        public EOrientation turnRight()   { return LEFT; }
    },
    LEFT(-1, 0) {
        @Override
        public EOrientation getOpposite() { return RIGHT; }
        @Override
        public EOrientation turnLeft()    { return DOWN; }
        @Override
        public EOrientation turnRight()   { return UP; }
    },
    RIGHT(1,0) {
        @Override
        public EOrientation getOpposite() { return LEFT; }
        @Override
        public EOrientation turnLeft()    { return UP; }
        @Override
        public EOrientation turnRight()   { return DOWN; }
    };

    private final int delta_x;
    private final int delta_y;
    private static final Random random = new Random();
    
    public abstract EOrientation getOpposite();
    public abstract EOrientation turnLeft();
    public abstract EOrientation turnRight();
    
    
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
        add_n_times_to_list(weighted_list, orientation, 10);
        add_n_times_to_list(weighted_list, orientation.getOpposite(), 1);
        add_n_times_to_list(weighted_list, orientation.turnLeft(), 3);
        add_n_times_to_list(weighted_list, orientation.turnRight(), 3);
        // picking random orientation from the weighted list
        final int r = random.nextInt(weighted_list.size());
        return weighted_list.get(r);
    }

    /**
     * To get a random direction depending on the given direction and on how long the character is proceeding forward.
     * The more the character is proceeding forward, the more likely it is to turn left or right.
     * @param orientation the given direction.
     * @param forward_counter the number of steps the character is proceeding forward.
     * @return a random direction weighted on the given direction and on how long the character is proceeding forward.
     */
    public static EOrientation getWeightedRandom(EOrientation orientation, final int forward_counter) {
        ArrayList<EOrientation> weighted_list = new ArrayList<>();
        add_n_times_to_list(weighted_list, orientation, 25);
        add_n_times_to_list(weighted_list, orientation.getOpposite(), forward_counter);
        add_n_times_to_list(weighted_list, orientation.turnLeft(), 3*forward_counter);
        add_n_times_to_list(weighted_list, orientation.turnRight(), 3*forward_counter);
        // picking random orientation from the weighted list
        final int r = random.nextInt(weighted_list.size());
        return weighted_list.get(r);
    }

    // helper method to add an orientation n times into a given list
    private static void add_n_times_to_list(List<EOrientation> list, EOrientation object, int n) {
        for (int i = 0; i <n; i++) {
            list.add(object);
        }
    }
}
