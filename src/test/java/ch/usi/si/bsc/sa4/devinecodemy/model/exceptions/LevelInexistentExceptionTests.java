package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("The LevelInexistentException")
public class LevelInexistentExceptionTests {

    LevelInexistentException levelInexistentException;

    @BeforeEach
    void setup() {
        levelInexistentException = new LevelInexistentException();
    }

    @Test
    @DisplayName("should be created after calling the constructor")
    public void testCreate() {

        assertNotNull(levelInexistentException,
                "object is null after calling the constructor");

        assertNotNull(levelInexistentException.getMessage(),
                "message is null after calling the constructor");
    }

    @Test
    @DisplayName("It should be created after calling the constructor with levelNumber as a parameter")
    public void testCreateWithNumber() {
        levelInexistentException = new LevelInexistentException(0);

        assertNotNull(levelInexistentException,
                "object is null after calling the constructor");

        assertNotNull(levelInexistentException.getMessage(),
                "message is null after calling the constructor");
    }
}
