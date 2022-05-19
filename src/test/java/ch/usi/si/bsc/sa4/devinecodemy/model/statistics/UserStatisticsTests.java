package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;


import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.language.ActionMoveForward;
import ch.usi.si.bsc.sa4.devinecodemy.model.language.Program;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("The UserStatistic")
public class UserStatisticsTests {

    private UserStatistics statistics1;
    private UserStatistics statistics2;

    private LevelValidation levelValidation;

    @BeforeEach
    void setup() {
        levelValidation = mock(LevelValidation.class);
        statistics1 = new UserStatistics("1");
        HashMap<Integer, LevelStatistics> levelData = new HashMap<>();
        levelData.put(1,new LevelStatistics(true));
        statistics2 = new UserStatistics("2", levelData);
    }

    @DisplayName("after creation")
    @Nested
    class AfterCreationTests {
        @Test
        @DisplayName("has the same id provided in the constructor")
        public void testGetId() {
            var actualId = statistics1.getId();
            var expectedId = "1";
            assertEquals(expectedId, actualId,
                    "id is not the same id provided in the constructor");
        }

        @Test
        @DisplayName("has empty level data if no data is provided")
        public void testGetDataEmpty() {
            var actualLength= statistics1.getData().keySet().size();
            var expectedLength = 0;
            assertEquals(expectedLength, actualLength,
                    "Level data is not empty if not given");
        }

        @Test
        @DisplayName("has the same level data provided in the constructor")
        public void testGetDataFull() {
            var actualLength= statistics2.getData().keySet().size();
            var expectedLength = 1;
            assertEquals(expectedLength, actualLength,
                    "Level data is not the same provided in the constructor");
        }

        @DisplayName("it should be possible to get the DTO of the object")
        @Test
        public void testToUserStatisticsDTO() {
            var actualDTO = statistics1.toUserStatisticsDTO();
            var expectedDTO = statistics1.toUserStatisticsDTO();
            assertEquals(expectedDTO.getId(),actualDTO.getId(),"the id of the UserStatisticsDTO is not the same of the UserStatistics");
            assertEquals(expectedDTO.getData().size(),actualDTO.getData().size(),"the data of the UserStatisticsDTO is not the same of the UserStatistics");
        }
    }

    @DisplayName("after adding an attempt")
    @Nested
    class AfterAttemptTests {

        @BeforeEach
        void addAttempt() {
            var program = new Program(List.of(new ActionMoveForward(null)));
            program = mock(Program.class);
            given(program.execute(any())).willReturn(levelValidation);

            given(levelValidation.isCompleted()).willReturn(false);
            statistics1.addData(1,"attempt0",false);
            given(levelValidation.isCompleted()).willReturn(true);
        }

        @DisplayName("it should be possible to retrieve the last attempt")
        @Test
        public void testGetAttemptFromLevel1() {
            var actualActions = statistics1.getAttemptFromLevel(1,0);
            var expectedActions = "attempt0";
            assertEquals(expectedActions, actualActions,
                    "The attempt received doesn't match the inserted commands");
        }

        @DisplayName("it should not be possible to retrieve the next level's statistics before completing the actual level")
        @Test
        public void testGetAttemptFromLevel2() {
            Executable actualActions = () -> statistics1.getAttemptFromLevel(2,0);

            assertThrows(StatisticInexistentException.class,
                    actualActions,
                    "The attempt received doesn't match the inserted commands");
        }

        @DisplayName("it should be possible to add another attempt to complete the same level")
        @Nested
        class AfterAfterAddingTests {

            @BeforeEach
            public void testAddDataCompleting() {
                statistics1.addData(1,"attempt1",true);
            }

            @DisplayName("it should be possible to add another attempt to the same level" +
                    " even after completing it, but without changing the completion state")
            @Test
            public void testAddDataAfterCompleting() {
                statistics1.addData(1,"attempt1",false);
            }
        }
    }
}
