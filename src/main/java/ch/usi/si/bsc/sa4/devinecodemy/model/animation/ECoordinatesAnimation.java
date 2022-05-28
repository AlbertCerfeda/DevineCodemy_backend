package ch.usi.si.bsc.sa4.devinecodemy.model.animation;

/**
 * The SAnimation class represents the standard animations recognized
 * by the frontend through their name.
 */
public enum ECoordinatesAnimation {
    TELEPORT_TO("TeleportTo"),
    TOGGLE_TELEPORT("ToggleTeleport"),
    ACTIVATE_TELEPORT_AT("AnimateAt"),
    DEACTIVATE_TELEPORT_AT("DeanimateAt"),
    ACTIVATE_LEVER("ActivateLever"),
    DEACTIVATE_LEVER("DeactivateLever");

    private final String name;

    ECoordinatesAnimation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}