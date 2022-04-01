package ch.usi.si.bsc.sa4.lab02spring.model;

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

    private final String name;

    EAnimation(String animationName) {
        this.name = animationName;
    }

    public String toString() {
        return name;
    }
}