package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("The InvalidAuthTokenException")
public class InvalidAuthTokenExceptionTests {

    InvalidAuthTokenException invalidAuthTokenException;

    @BeforeEach
    void setup() {
        invalidAuthTokenException = new InvalidAuthTokenException();
    }

    @Test
    @DisplayName("should be created after calling the constructor")
    public void testCreate() {
        assertNotNull(invalidAuthTokenException,
                "object is null after calling the constructor");

        assertNotNull(invalidAuthTokenException.getMessage(),
                "message is null after calling the constructor");
    }
}
