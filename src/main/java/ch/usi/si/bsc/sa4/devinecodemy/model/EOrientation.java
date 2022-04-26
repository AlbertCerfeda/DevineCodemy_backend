package ch.usi.si.bsc.sa4.devinecodemy.model;

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
}
