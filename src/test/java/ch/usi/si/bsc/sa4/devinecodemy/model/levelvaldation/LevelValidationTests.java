package ch.usi.si.bsc.sa4.devinecodemy.model.levelvaldation;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("The LevelValidation")
public class LevelValidationTests {

    LevelValidation levelValidation;

    @BeforeEach
    void setup() {
        levelValidation = new LevelValidation();
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("it should be able to check if the level is completed")
        @Test
        public void testIsCompleted() {
            var actualCompletion = levelValidation.isCompleted();
            assertFalse(actualCompletion,
                    "completed is not false on creation");
        }

        @DisplayName("it should be able to get all the errors")
        @Test
        public void testGetErrors() {
            var actualErrors = levelValidation.getErrors();
            var expectedErrors = List.of();
            assertEquals(expectedErrors, actualErrors,
                    "error list is not empty on creation");
        }

        @DisplayName("it should be able to get all the animation")
        @Test
        public void testGetAnimation() {
            var actualAnimations = levelValidation.getAnimations();
            var expectedAnimations = List.of();
            assertEquals(expectedAnimations, actualAnimations,
                    "animations list is not empty on creation");
        }

        @DisplayName("it should be able to set the completion status")
        @Test
        public void testSetCompleted() {
            levelValidation.setCompleted(true);
            assertTrue(levelValidation.isCompleted(),
                    "completion is not the one expected after modifying it");
        }

        @DisplayName("it should be able to add an error")
        @Test
        public void testAddError() {
            levelValidation.addError("Error");
            assertTrue(levelValidation.getErrors().contains("Error"),
                    "error list doesn't contain the new error after adding it");
            assertEquals(levelValidation.getErrors().size(),1,
                    "error list does not have the expected size after adding error");
        }

        @DisplayName("it should be able to add an animation")
        @Test
        public void testAddAnimation() {
            levelValidation.addAnimation(ERobotAnimation.EMOTE_DANCE);
            assertTrue(levelValidation.getAnimations().contains(ERobotAnimation.EMOTE_DANCE),
                    "animation list doesn't contain the new animation");
            assertEquals(levelValidation.getAnimations().size(),1,
                    "animation list does not have the expected size");
        }

        @DisplayName("after adding animation and error")
        @Nested
        class AfterAddingAnimationAndErrorTests {

            @BeforeEach
            void setup() {
                levelValidation.addAnimation(ERobotAnimation.EMOTE_DANCE);
                levelValidation.addError("Error!");
            }

            @DisplayName("it should be able to get the animations as strings")
            @Test
            public void testGetAnimationsAsStrings() {
                var actualAnimationsStrings = levelValidation.getAnimationsAsStrings();
                var expectedAnimationsStrings = List.of("EmoteDance");
                assertTrue(levelValidation.getAnimations().contains(ERobotAnimation.EMOTE_DANCE),
                        "animation list doesn't contain the new animation");
                assertEquals(levelValidation.getAnimations().size(),1,
                        "animation list does not have the expected size");
                assertEquals(expectedAnimationsStrings,actualAnimationsStrings,
                        "animation conversion to string failed");
            }

            @DisplayName("it should be able to clear the animations")
            @Test
            public void testClearAnimations() {
                levelValidation.clearAnimations();
                assertFalse(levelValidation.getAnimations().contains(ERobotAnimation.EMOTE_DANCE),
                        "animation list contains the old animation");
                assertEquals(levelValidation.getAnimations().size(),0,
                        "animation list is not empty after clearing it");
            }

            @DisplayName("it should be able to return the DTO of the object")
            @Test
            public void testToLevelValidationDTO() {
                var actualDTO = levelValidation.toLevelValidationDTO();
                var expectedDTO = new LevelValidationDTO(levelValidation);
                assertEquals(expectedDTO.isCompleted(),actualDTO.isCompleted(),
                        "the error list in the new dto is not the same as the starting object");
                assertEquals(expectedDTO.getAnimations(),actualDTO.getAnimations(),
                        "the animation list in the new dto is not the same as the starting object");
                assertEquals(expectedDTO.getErrors(),actualDTO.getErrors(),
                        "the error list in the new dto is not the same as the starting object");
            }

            @DisplayName("it should be able to compare two objects")
            @Test
            public void testEquals() {
                LevelValidation levelValidation1 = new LevelValidation();
                levelValidation1.setCompleted(true);
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different completed and animations" +
                                " returns true");
                levelValidation1.setCompleted(false);
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different errors and animations" +
                                " returns true");
                levelValidation1.addError("Error!");
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different animations"+
                                " returns true");
                levelValidation1.addAnimation(ERobotAnimation.EMOTE_DANCE);
                assertEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing the same fields returns false");
                levelValidation1.setCompleted(true);
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different completed returns true");
                levelValidation1.clearAnimations();
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different completed and animations" +
                        " returns true");
                levelValidation1.addError("A new Error!");
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different completed, errors " +
                                " and animations returns true");
                levelValidation1.addAnimation(ERobotAnimation.EMOTE_DANCE);
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different completed " +
                                " and errors returns true");
                levelValidation1.addAnimation(ERobotAnimation.JUMP);
                assertNotEquals(levelValidation,levelValidation1,
                        "the comparison of an object with one containing different animations " +
                                " returns true");
                assertEquals(levelValidation,levelValidation,
                        "the comparison of an object with itself returns false");
                assertNotEquals(levelValidation, new LevelValidationDTO(levelValidation),
                        "the comparison of an object with an object of another class returns true");
            }

            @DisplayName("it should be able to get the hash of the object")
            @Test
            public void testHashCode() {
                var actualHashCode = levelValidation.hashCode();
                var expectHashCode = Objects.hash(levelValidation.isCompleted(),
                        levelValidation.getErrors(),
                        levelValidation.getAnimations());
                assertEquals(expectHashCode,actualHashCode,
                        "the hash of the object is not the one expected");
            }
        }
    }
}
