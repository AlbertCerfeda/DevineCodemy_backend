package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("The UserAlreadyExistsException")
public class UserAlreadyExistsExceptionTests {

    UserAlreadyExistsException userAlreadyExistsException;

    @BeforeEach
    void setup() {
        userAlreadyExistsException = new UserAlreadyExistsException();
    }

    @DisplayName("should be created after calling the constructor")
    @Test
    public void testCreate() {
        assertNotNull(userAlreadyExistsException,
                "object is null after calling the constructor");
        assertNotNull(userAlreadyExistsException.getMessage(),
                "message is null after calling the constructor");
    }

    @DisplayName("should be created after calling the constructor with user id"
            + " as a parameter")
    @Test
    public void testCreateWithNumber() {
        userAlreadyExistsException = new UserAlreadyExistsException("an id");
        assertNotNull(userAlreadyExistsException,
                "object is null after calling the constructor");
        assertNotNull(userAlreadyExistsException.getMessage(),
                "message is null after calling the constructor");
    }
}
