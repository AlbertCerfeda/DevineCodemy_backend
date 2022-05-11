package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserNotAllowedException;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("The level service")
public class LevelServiceTests {

    @Autowired
    LevelService levelService;

    @MockBean
    GamePlayer gamePlayer;
    @MockBean
    UserService userService;
    @MockBean
    StatisticsService statisticsService;

    @Autowired
    LevelRepository levelRepository;

    @BeforeEach
    void testSetup() {
        var level = levelRepository.findAll().get(0);
        var game = new GamePlayer(level);

        // user 1 completed level 1
        var validation1 = new LevelValidation();
        validation1.setCompleted(true);
        var stats1 = new UserStatistics("1");
        stats1.addData(game, validation1);

        // user 2 completed no levels
        var validation2 = new LevelValidation();
        var stats2 = new UserStatistics("2");
        stats2.addData(game, validation2);

        given(userService.userIdExists(any())).willReturn(false);
        given(userService.userIdExists("1")).willReturn(true);
        given(userService.userIdExists("2")).willReturn(true);
        given(userService.userIdExists("3")).willReturn(true);

        given(statisticsService.getById(any())).willReturn(Optional.empty());
        given(statisticsService.getById("1")).willReturn(Optional.of(stats1));
        given(statisticsService.getById("2")).willReturn(Optional.of(stats2));
        given(statisticsService.addStats("3")).willReturn(new UserStatistics("3"));

        given(gamePlayer.play(any())).willReturn(validation2);
        given(gamePlayer.play(List.of())).willReturn(validation1);
    }

    @DisplayName(" returns two playable levels when user exists")
    @Test
    void testGetAllPlayableLevelsTwo() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("1");
        var expectedPlayableLevels = List.of(
                levelRepository.findAll().get(0),
                levelRepository.findAll().get(1)
        );
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
        assertThrows(UserInexistentException.class, () -> levelService.getAllPlayableLevels("4"), "should throw");
    }

    @DisplayName(" returns one playable level when user exists and has stats")
    @Test
    void testGetAllPlayableLevelsOneWithStats() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("2");
        var expectedPlayableLevels = List.of(
                levelRepository.findAll().get(0)
        );
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
    }

    @DisplayName(" returns one playable level when user exists and has no stats")
    @Test
    void testGetAllPlayableLevelsOneWithoutStats() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("3");
        var expectedPlayableLevels = List.of(
                levelRepository.findAll().get(0)
        );
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
    }

    @DisplayName(" throws exception when getting all playable levels of nonexistent user")
    @Test
    void testGetAllPlayableLevelsNone() {
        assertThrows(UserInexistentException.class, () -> levelService.getAllPlayableLevels("4"), "should throw exception when getting all playable levels of nonexistent user");
    }

    public static Stream<Arguments> getByLevelNumberIfPlayableTestsAndIsLevelPlayableTestsArgumentProvider() {
        return Stream.of(
                arguments(1, "1", 1, null),
                arguments(2, "1", 2, null),
                arguments(1, "2", 1, null),
                arguments(2, "2", 0, null),
                arguments(1, "3", 1, null),
                arguments(2, "3", 0, null),
                arguments(20, "3", -1, LevelInexistentException.class),
                arguments(1, "4", -1, UserInexistentException.class)
        );
    }

    @ParameterizedTest(name = "checking if level by number {0} and user {1} is playable should return {2} > 0 and should throw {3}")
    @MethodSource("getByLevelNumberIfPlayableTestsAndIsLevelPlayableTestsArgumentProvider")
    void isLevelPlayableTest(int levelNumber, String userId, int expected, Class<Exception> expectedType) {
        if (expectedType == null) {
            assertEquals(expected > 0, levelService.isLevelPlayable(levelNumber, userId), "doesn't match");
        } else {
            assertThrows(expectedType, () -> levelService.isLevelPlayable(levelNumber, userId), "should throw");
        }
    }

    @DisplayName(" returns the correct level by number")
    @Test
    void testGetByLevelNumber() {
        var actualOptionalLevel = levelService.getByLevelNumber(1);
        var expectedOptionalLevel = levelRepository.findByLevelNumber(1);
        assertEquals(expectedOptionalLevel, actualOptionalLevel, "levels don't match");
    }

    @ParameterizedTest(name = "getting level by number {0} and user {1} should return level {2} and should throw {3}")
    @MethodSource("getByLevelNumberIfPlayableTestsAndIsLevelPlayableTestsArgumentProvider")
    void getByLevelNumberIfPlayableTest(int levelNumber, String userId, int expectedLevelNumber, Class<Exception> expectedType) {
        if (expectedType == null) {
            var expected = expectedLevelNumber > 0
                    ? levelRepository.findByLevelNumber(expectedLevelNumber)
                    : Optional.empty();
            assertEquals(expected, levelService.getByLevelNumberIfPlayable(levelNumber, userId), "doesn't match");
        } else {
            assertThrows(expectedType, () -> levelService.getByLevelNumberIfPlayable(levelNumber, userId), "should throw");
        }
    }

    @DisplayName(" returns the correct level exists by number")
    @Test
    void testLevelExists() {
        var actual = levelService.levelExists(2);
        var expected = levelRepository.existsByLevelNumber(2);
        assertEquals(expected, actual, "level exists don't match");
    }

    public static Stream<Arguments> getLevelNumberRangeForWorldTestsArgumentProvider() {
        return Stream.of(
                arguments(EWorld.EARTH, Pair.of(1, 5)), // test Earth levels
                arguments(EWorld.LAVA, Pair.of(-1, -1)), // test Lava levels
                arguments(EWorld.SKY, Pair.of(6, 10)) // test Sky levels
        );
    }

    @ParameterizedTest(name = "getting level number range for world {0} should return {1}")
    @MethodSource("getLevelNumberRangeForWorldTestsArgumentProvider")
    void getLevelNumberRangeForWorldTest(EWorld world, Pair<Integer, Integer> expected) {
        final Pair<Integer, Integer> actual = levelService.getLevelNumberRangeForWorld(world);
        assertEquals(expected, actual);
    }

    @DisplayName(" correctly plays a level with valid commands")
    @Test
    void testPlayLevelValid() {
        var expected = new LevelValidation();
        expected.setCompleted(true);
        expected.addAnimation(EAnimation.MOVE_FORWARD);
        expected.addAnimation(EAnimation.MOVE_FORWARD);
        expected.addAnimation(EAnimation.MOVE_FORWARD);
        expected.addAnimation(EAnimation.MOVE_FORWARD);
        expected.addAnimation(EAnimation.JUMP);
        expected.addAnimation(EAnimation.EMOTE_DANCE);
        assertEquals(expected, levelService.playLevel(1, "1", List.of("moveForward","moveForward","moveForward","moveForward","collectCoin")), "validations don't match");
    }

    @DisplayName(" correctly plays a level with invalid commands")
    @Test
    void testPlayLevelInvalid() {
        var expected = new LevelValidation();
        expected.setCompleted(false);
        assertEquals(expected, levelService.playLevel(1, "1", List.of()), "validations don't match");
    }

    @DisplayName(" correctly plays a level with exception")
    @Test
    void testPlayLevelException() {
        assertThrows(LevelInexistentException.class, () -> levelService.playLevel(20, "3", List.of()), "should throw on no level");
        assertThrows(UserInexistentException.class, () -> levelService.playLevel(1, "4", List.of()), "should throw on no user");
        assertThrows(UserNotAllowedException.class, () -> levelService.playLevel(10, "2", List.of()), "should throw on high level for user");
    }
}
