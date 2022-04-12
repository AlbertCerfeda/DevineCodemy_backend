package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The level")
public class LevelTests {

    private Level level;

    @BeforeEach
    void setup() {
        level = new Level("test name", "test description", 10, null, null, List.of());
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {

        @DisplayName("has the same name provided in the constructor")
        @Test
        void testGetName() {
            var actualName = level.getName();
            var expectedName = "test name";
            assertEquals(expectedName, actualName, "name is not the one provided in the constructor");
        }

        @DisplayName("has the same description provided in the constructor")
        @Test
        void testGetDescription() {
            var actualDescription = level.getDescription();
            var expectedDescription = "test description";
            assertEquals(expectedDescription, actualDescription, "description is not the one provided in the constructor");
        }

    }

}
