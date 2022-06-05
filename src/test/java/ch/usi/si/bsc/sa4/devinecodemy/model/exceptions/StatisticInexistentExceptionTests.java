package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("The StatisticInexistentException")
public class StatisticInexistentExceptionTests {

    StatisticInexistentException statisticInexistentException;

    @BeforeEach
    void setup() {
        statisticInexistentException = new StatisticInexistentException();
    }

    @DisplayName("should be created after calling the constructor")
    @Test
    public void testCreate() {
        assertNotNull(statisticInexistentException,
                "object is null after calling the constructor");

        assertNotNull(statisticInexistentException.getMessage(),
                "message is null after calling the constructor");
    }
}
