package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

/**
 * The TAnimation class represents the teleport animations recognized
 * by the frontend through their name.
 */
public class CoordinatesAnimation implements Animation {

    private final ECoordinatesAnimation animation;
    private final int x;
    private final int y;
    private final int z;

    public CoordinatesAnimation(ECoordinatesAnimation animation, int x, int y, int z) {
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return animation.getName() + "(" + x + "," + y + "," + z + ")";
    }

}
