package ch.usi.si.bsc.sa4.devinecodemy.model;


/**
 * The EOrientation class represents all the possible orientations
 * that the robot can have.
 */
public enum EOrientation {
    UP(0, -1) {
        @Override
        public EOrientation getOpposite() {
            return DOWN;
        }

        @Override
        public EOrientation turnLeft() {
            return LEFT;
        }

        @Override
        public EOrientation turnRight() {
            return RIGHT;
        }
    },
    DOWN(0, 1) {
        @Override
        public EOrientation getOpposite() {
            return UP;
        }

        @Override
        public EOrientation turnLeft() {
            return RIGHT;
        }

        @Override
        public EOrientation turnRight() {
            return LEFT;
        }
    },
    LEFT(-1, 0) {
        @Override
        public EOrientation getOpposite() {
            return RIGHT;
        }

        @Override
        public EOrientation turnLeft() {
            return DOWN;
        }

        @Override
        public EOrientation turnRight() {
            return UP;
        }
    },
    RIGHT(1, 0) {
        @Override
        public EOrientation getOpposite() {
            return LEFT;
        }

        @Override
        public EOrientation turnLeft() {
            return UP;
        }

        @Override
        public EOrientation turnRight() {
            return DOWN;
        }
    };

    private final int deltaX;
    private final int deltaY;

    public abstract EOrientation getOpposite();

    public abstract EOrientation turnLeft();

    public abstract EOrientation turnRight();


    /**
     * Private constructor for enum values.
     * Each value is associated with a deltaX and a deltaY
     * which together mathematically represent the direction.
     *
     * @param deltaX the x variation.
     * @param deltaY the y variation.
     */
    EOrientation(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Returns the x direction.
     *
     * @return the x direction.
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Returns the y direction.
     *
     * @return the y direction.
     */
    public int getDeltaY() {
        return deltaY;
    }
}
