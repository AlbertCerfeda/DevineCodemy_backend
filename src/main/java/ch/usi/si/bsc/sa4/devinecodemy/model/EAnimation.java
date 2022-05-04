package ch.usi.si.bsc.sa4.devinecodemy.model;

/**
 * Animations recognized by the frontend.
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
    EMOTE_DANCE("EmoteDance");
    
    // The animation name recognized in the frontend
    private final String name;

    EAnimation(String animationName) {
        this.name = animationName;
    }

    @Override
    public String toString() {
        return name;
    }
}