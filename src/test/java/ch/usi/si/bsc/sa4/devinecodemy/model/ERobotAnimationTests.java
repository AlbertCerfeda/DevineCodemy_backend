package ch.usi.si.bsc.sa4.devinecodemy.model;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The user")
public class ERobotAnimationTests {

    ERobotAnimation moveForward;
    ERobotAnimation turnLeft;
    ERobotAnimation turnRight;
    ERobotAnimation jump;
    ERobotAnimation idle;
    ERobotAnimation death;
    ERobotAnimation no;
    ERobotAnimation thumbsUp;
    ERobotAnimation dance;

    @BeforeEach
    void setup() {
        moveForward = ERobotAnimation.MOVE_FORWARD;
        turnLeft = ERobotAnimation.TURN_LEFT;
        turnRight = ERobotAnimation.TURN_RIGHT;
        jump = ERobotAnimation.JUMP;
        idle = ERobotAnimation.IDLE;
        death = ERobotAnimation.EMOTE_DEATH;
        no = ERobotAnimation.EMOTE_NO;
        thumbsUp = ERobotAnimation.EMOTE_THUMBS_UP;
        dance = ERobotAnimation.EMOTE_DANCE;
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
