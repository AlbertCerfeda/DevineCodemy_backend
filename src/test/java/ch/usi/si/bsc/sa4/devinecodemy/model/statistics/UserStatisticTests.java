package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("In the UserStatistic class")
public class UserStatisticTests {

    private UserStatistics statistics1;
    private UserStatistics statistics2;

    @BeforeEach
    void setup() {
        statistics1 = new UserStatistics("1");
        statistics2 = new UserStatistics("2", new HashMap<>());
    }

    @Test
    @DisplayName("Should create a User statistic on empty lists")
    public void idShouldBeThereAfterCreation() {
        var actualId = statistics1.getId();
        var expectedId = "1";
        assertEquals(expectedId, actualId,
                "id is not the same id provided in the constructor");
    }
}
