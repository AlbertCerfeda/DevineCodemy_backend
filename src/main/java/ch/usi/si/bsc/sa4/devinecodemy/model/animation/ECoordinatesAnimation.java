package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

/**
 * The SAnimation class represents the standard animations recognized
 * by the frontend through their name.
 */
public enum ECoordinatesAnimation {
    TELEPORT_TO("TeleportTo"),
    ACTIVATE_TELEPORT_AT("AnimateAt"),
    ACTIVATE_LEVER("ActivateLever");

    private final String name;

    ECoordinatesAnimation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}