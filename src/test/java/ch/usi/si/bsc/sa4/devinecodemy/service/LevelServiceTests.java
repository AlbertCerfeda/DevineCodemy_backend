package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserNotAllowedException;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @DisplayName(" returns correctly whether a level is player given a user")
    @Test
    void testIsLevelPlayable() {
        assertTrue(levelService.isLevelPlayable(1, "1"), "doesn't match");
        assertTrue(levelService.isLevelPlayable(2, "1"), "doesn't match");
        assertTrue(levelService.isLevelPlayable(1, "2"), "doesn't match");
        assertFalse(levelService.isLevelPlayable(2, "2"), "doesn't match");
        assertTrue(levelService.isLevelPlayable(1, "3"), "doesn't match");
        assertFalse(levelService.isLevelPlayable(2, "3"), "doesn't match");
        assertThrows(LevelInexistentException.class, () -> levelService.isLevelPlayable(20, "3"), "should throw on no level");
        assertThrows(UserInexistentException.class, () -> levelService.isLevelPlayable(1, "4"), "should throw on no user");
    }

    @DisplayName(" returns the correct level by number")
    @Test
    void testGetByLevelNumber() {
        var actualOptionalLevel = levelService.getByLevelNumber(1);
        var expectedOptionalLevel = levelRepository.findByLevelNumber(1);
        assertEquals(expectedOptionalLevel, actualOptionalLevel, "levels don't match");
    }

    @DisplayName(" returns the correct level by number")
    @Test
    void testGetByLevelNumberIfPlayable() {
        assertEquals(levelRepository.findByLevelNumber(1), levelService.getByLevelNumberIfPlayable(1, "1"), "doesn't match");
        assertEquals(levelRepository.findByLevelNumber(2), levelService.getByLevelNumberIfPlayable(2, "1"), "doesn't match");
        assertEquals(levelRepository.findByLevelNumber(1), levelService.getByLevelNumberIfPlayable(1, "2"), "doesn't match");
        assertEquals(Optional.empty(), levelService.getByLevelNumberIfPlayable(2, "2"), "doesn't match");
        assertEquals(levelRepository.findByLevelNumber(1), levelService.getByLevelNumberIfPlayable(1, "3"), "doesn't match");
        assertEquals(Optional.empty(), levelService.getByLevelNumberIfPlayable(2, "3"), "doesn't match");
        assertThrows(LevelInexistentException.class, () -> levelService.getByLevelNumberIfPlayable(20, "3"), "should throw on no level");
        assertThrows(UserInexistentException.class, () -> levelService.getByLevelNumberIfPlayable(1, "4"), "should throw on no user");
    }

    @DisplayName(" returns the correct level exists by number")
    @Test
    void testLevelExists() {
        var actual = levelService.levelExists(2);
        var expected = levelRepository.existsByLevelNumber(2);
        assertEquals(expected, actual, "level exists don't match");
    }

    @DisplayName(" correctly plays a level")
    @Test
    void testPlayLevel() {
        var expected1 = new LevelValidation();
        expected1.setCompleted(true);
        expected1.addAnimation(EAnimation.MOVE_FORWARD);
        expected1.addAnimation(EAnimation.MOVE_FORWARD);
        expected1.addAnimation(EAnimation.MOVE_FORWARD);
        expected1.addAnimation(EAnimation.MOVE_FORWARD);
        expected1.addAnimation(EAnimation.JUMP);
        expected1.addAnimation(EAnimation.EMOTE_DANCE);
        assertEquals(expected1, levelService.playLevel(1, "1", List.of("moveForward","moveForward","moveForward","moveForward","collectCoin")), "validations don't match");
        var expected2 = new LevelValidation();
        expected2.setCompleted(false);
        assertEquals(expected2, levelService.playLevel(1, "1", List.of()), "validations don't match");
        assertThrows(LevelInexistentException.class, () -> levelService.playLevel(20, "3", List.of()), "should throw on no level");
        assertThrows(UserInexistentException.class, () -> levelService.playLevel(1, "4", List.of()), "should throw on no user");
        assertThrows(UserNotAllowedException.class, () -> levelService.playLevel(10, "2", List.of()), "should throw on high level for user");
    }

}
