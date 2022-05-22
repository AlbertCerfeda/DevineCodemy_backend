package ch.usi.si.bsc.sa4.devinecodemy.model;

import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;

/**
 * The EAnimation class represents the animation recognized
 * by the frontend through its name.
 */
public enum EAnimation {
    MOVE_FORWARD("MoveForward"),
    TURN_LEFT("TurnLeft"),
    TURN_RIGHT("TurnRight"),
    JUMP("Jump"),
    IDLE("Idle"),
    EMOTE_DEATH("EmoteDeath"),
    EMOTE_NO("EmoteNo"),
    EMOTE_THUMBS_UP("EmoteThumbsUp"),
    EMOTE_DANCE("EmoteDance"),
    TELEPORT_TO("TeleportTo"),
    ACTIVATE_TELEPORT_AT("AnimateAt"),
    ACTIVATE_LEVER("ActivateLever");
    
    /** The animation name recognized in the frontend */
    private final String name;


    // BAD CODE: I coded it just to test the logic of the game.
    // TODO: refactor this with cleaner code for the teleport:
    //  create an interface extended both by the animations that takes coordinates and the standard animations
    //  cannot extend an Enum in java
    private int targetX=0;
    private int targetY=0;

    EAnimation(String animationName) {
        this.name = animationName;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    @Override
    public String toString() {
        if (this == TELEPORT_TO || this == ACTIVATE_TELEPORT_AT || this == ACTIVATE_LEVER) {
            return name + "(" + targetX + "," + targetY + ")";
        }
        return name;
    }
}