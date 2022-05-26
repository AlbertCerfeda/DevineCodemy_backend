package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

/**
 * The TAnimation class represents the teleport animations recognized
 * by the frontend through their name.
 */
public class CoordinatesTargetAnimation extends CoordinatesAnimation {

    private final int targetX;
    private final int targetY;
    private final int targetZ;

    public CoordinatesTargetAnimation(ECoordinatesAnimation animation, int x, int y, int z, int targetX, int targetY, int targetZ) {
        super(animation, x, y,z);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + targetX + "," + targetY + "," + targetZ + ")";
    }

}
