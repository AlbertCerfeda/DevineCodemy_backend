package ch.usi.si.bsc.sa4.devinecodemy.model;

/**
 * The TAnimation class represents the teleport animations recognized
 * by the frontend through their name.
 */
public enum TAnimation implements Animation {
    TELEPORT_TO("TeleportTo"),
    ACTIVATE_TELEPORT_AT("AnimateAt"),
    ACTIVATE_LEVER("ActivateLever");

    /** The animation name recognized in the frontend */
    private final String name;

    private int targetX=0;

    private int targetY=0;

    TAnimation(String animationName) {
        this.name = animationName;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public String toString() {
        return name + "(" + targetX + "," + targetY + ")";
    }

}
