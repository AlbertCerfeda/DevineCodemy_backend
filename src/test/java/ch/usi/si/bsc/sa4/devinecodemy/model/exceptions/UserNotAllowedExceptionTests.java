package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("The UserNotAllowedException")
public class UserNotAllowedExceptionTests {

    UserNotAllowedException userNotAllowedException;

    @BeforeEach
    void setup() {
        userNotAllowedException = new UserNotAllowedException("a message");
    }

    @Test
    @DisplayName("It should be created after calling the constructor")
    public void testCreate() {
        assertNotNull(userNotAllowedException,
                "object is null after calling the constructor");
        assertEquals("a message",userNotAllowedException.getMessage(),
                "message is not the one provided in the constructor");
    }

    @Test
    @DisplayName("After calling the constructor with id and levelNumber as parameters")
    public void testCreateWithNumber() {
        userNotAllowedException = new UserNotAllowedException("an id",0);
        assertNotNull(userNotAllowedException,
                "object is null after calling the constructor");
        assertNotNull(userNotAllowedException.getMessage(),
                "message is null after creation");
    }
}
