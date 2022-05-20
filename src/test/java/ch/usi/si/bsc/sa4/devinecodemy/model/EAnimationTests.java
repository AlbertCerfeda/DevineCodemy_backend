package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The user")
public class EAnimationTests {

    EAnimation moveForward;
    EAnimation turnLeft;
    EAnimation turnRight;
    EAnimation jump;
    EAnimation idle;
    EAnimation death;
    EAnimation no;
    EAnimation thumbsUp;
    EAnimation dance;

    @BeforeEach
    void setup() {
        moveForward = EAnimation.MOVE_FORWARD;
        turnLeft = EAnimation.TURN_LEFT;
        turnRight = EAnimation.TURN_RIGHT;
        jump = EAnimation.JUMP;
        idle = EAnimation.IDLE;
        death = EAnimation.EMOTE_DEATH;
        no = EAnimation.EMOTE_NO;
        thumbsUp = EAnimation.EMOTE_THUMBS_UP;
        dance = EAnimation.EMOTE_DANCE;
    }

    @DisplayName("should be possible to get the string representation after creation")
    @Test
    public void testToString() {
        assertEquals("MoveForward", moveForward.toString(),
                "string representation doesn't match the name");
        assertEquals("TurnLeft", turnLeft.toString(),
                "string representation doesn't match the name");
        assertEquals("TurnRight", turnRight.toString(),
                "string representation doesn't match the name");
        assertEquals("Jump", jump.toString(),
                "string representation doesn't match the name");
        assertEquals("Idle", idle.toString(),
                "string representation doesn't match the name");
        assertEquals("EmoteDeath", death.toString(),
                "string representation doesn't match the name");
        assertEquals("EmoteNo", no.toString(),
                "string representation doesn't match the name");
        assertEquals("EmoteThumbsUp", thumbsUp.toString(),
                "string representation doesn't match the name");
        assertEquals("EmoteDance", dance.toString(),
                "string representation doesn't match the name");

    }
}
