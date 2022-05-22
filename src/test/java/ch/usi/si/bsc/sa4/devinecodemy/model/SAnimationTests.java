package ch.usi.si.bsc.sa4.devinecodemy.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The user")
public class SAnimationTests {

    SAnimation moveForward;
    SAnimation turnLeft;
    SAnimation turnRight;
    SAnimation jump;
    SAnimation idle;
    SAnimation death;
    SAnimation no;
    SAnimation thumbsUp;
    SAnimation dance;

    @BeforeEach
    void setup() {
        moveForward = SAnimation.MOVE_FORWARD;
        turnLeft = SAnimation.TURN_LEFT;
        turnRight = SAnimation.TURN_RIGHT;
        jump = SAnimation.JUMP;
        idle = SAnimation.IDLE;
        death = SAnimation.EMOTE_DEATH;
        no = SAnimation.EMOTE_NO;
        thumbsUp = SAnimation.EMOTE_THUMBS_UP;
        dance = SAnimation.EMOTE_DANCE;
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
