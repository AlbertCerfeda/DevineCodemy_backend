package ch.usi.si.bsc.sa4.devinecodemy.model;

/**
 * The SAnimation class represents the standard animations recognized
 * by the frontend through their name.
 */
public enum SAnimation implements Animation {
    MOVE_FORWARD("MoveForward"),
    TURN_LEFT("TurnLeft"),
    TURN_RIGHT("TurnRight"),
    JUMP("Jump"),
    IDLE("Idle"),
    EMOTE_DEATH("EmoteDeath"),
    EMOTE_NO("EmoteNo"),
    EMOTE_THUMBS_UP("EmoteThumbsUp"),
    EMOTE_DANCE("EmoteDance");

    
    /** The animation name recognized in the frontend */
    private final String name;


    SAnimation(String animationName) {
        this.name = animationName;
    }

    @Override
    public String toString() {
        return name;
    }
}