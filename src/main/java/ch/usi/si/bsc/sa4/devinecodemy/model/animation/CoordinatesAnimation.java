package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

/**
 * The TAnimation class represents the teleport animations recognized
 * by the frontend through their name.
 */
public class CoordinatesAnimation implements Animation {

    private final ECoordinatesAnimation animation;
    private final int targetX;
    private final int targetY;
    private final int targetZ;

    public CoordinatesAnimation(ECoordinatesAnimation animation, int targetX, int targetY, int targetZ) {
        this.animation = animation;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    @Override
    public String toString() {
        return animation.getName() + "(" + targetX + "," + targetY + "," + targetZ + ")";
    }

}
