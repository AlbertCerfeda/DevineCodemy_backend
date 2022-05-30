package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserNotAllowedException;
import ch.usi.si.bsc.sa4.devinecodemy.model.language.Program;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("The level service")
public class LevelServiceTests {

    LevelService levelService;

    @Mock
    UserService userService;
    @Mock
    StatisticsService statisticsService;
    @Mock
    LevelRepository levelRepository;

    Program program;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level5;
    private Level level6;
    private Level level10;
    private Level level11;
    private Level level15;
    static private EWorld inferno;
    static private EWorld purgatory;
    static private EWorld paradise;


    @BeforeAll
    void init(){
        levelService = new LevelService(levelRepository,statisticsService,userService);
        inferno = EWorld.PARADISE;
        purgatory = EWorld.INFERNO;
        paradise = EWorld.PURGATORY;
    }

    @BeforeEach
    void testSetup() {
        program = mock(Program.class);
        String name = "level ";
        String desc = "description ";
        int n = 0;
        EWorld world = inferno;
        int maxCommands = 0;
        List<Tile> grid = List.of();
        Robot robot = new Robot(0,0, EOrientation.UP);
        String thumbnail = "../assets/thumbnailSrc";

        level1 = new Level(name+"1",desc+"1",n+1, world, maxCommands+5,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS),thumbnail+"1");
        level2 = new Level(name+"2",desc+"2",n+2, world, maxCommands+10,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS),thumbnail+"2");
        level3 = new Level(name+"3",desc+"3",n+3, world, maxCommands+15,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS),thumbnail+"3");
        level5 = new Level(name+"5",desc+"5",n+5, world, maxCommands+25,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS),thumbnail+"5");
        world = purgatory;
        level6 = new Level(name+"6",desc+"6",n+6, world, maxCommands+30,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS,ECategory.LOGIC,ECategory.CONDITIONS),thumbnail+"6");
        level10 = new Level(name+"10",desc+"10",n+10, world, maxCommands+50,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS,ECategory.LOGIC,ECategory.CONDITIONS),thumbnail+"10");
        world = purgatory;
        level11 = new Level(name+"11",desc+"11",n+11, world, maxCommands+55,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS,ECategory.LOGIC,ECategory.CONDITIONS,ECategory.LOOPS,ECategory.FUNCTIONS),
                thumbnail+"11");
        level15 = new Level(name+"15",desc+"15",n+15, world, maxCommands+70,
                new Board(grid,List.of(),0),robot,
                List.of(ECategory.BASIC_COMMANDS,ECategory.LOGIC,ECategory.CONDITIONS,ECategory.LOOPS,ECategory.FUNCTIONS),
                thumbnail+"15");

        var validation1 = new LevelValidation();
        // user 1 completed level 1
        var stats1 = new UserStatistics("1");
        stats1.addData(1, "attempt1", true);

        // user 2 completed no levels
        var stats2 = new UserStatistics("2");
        stats2.addData(2, "attempt1", false);

        // user 3 completed levels 1 and 2
        var stats5 = new UserStatistics("5`");
        stats5.addData(1, "attempt1",true);
        stats5.addData(2, "attempt1",true);

        given(levelRepository.existsByLevelNumber(anyInt())).willReturn(false);
        given(levelRepository.existsByLevelNumber(1)).willReturn(true);
        given(levelRepository.existsByLevelNumber(2)).willReturn(true);
        given(levelRepository.existsByLevelNumber(3)).willReturn(true);
        given(levelRepository.existsByLevelNumber(5)).willReturn(true);
        given(levelRepository.existsByLevelNumber(6)).willReturn(true);
        given(levelRepository.existsByLevelNumber(10)).willReturn(true);
        given(levelRepository.existsByLevelNumber(11)).willReturn(true);
        given(levelRepository.existsByLevelNumber(15)).willReturn(true);
        given(levelRepository.findAll()).willReturn(List.of(level1,level2,level3,level5,level6,level10,level11,level15));

        given(levelRepository.findByLevelNumber(anyInt())).willReturn(Optional.empty());
        given(levelRepository.findByLevelNumber(1)).willReturn(Optional.of(level1));
        given(levelRepository.findByLevelNumber(2)).willReturn(Optional.of(level2));
        given(levelRepository.findByLevelNumber(3)).willReturn(Optional.of(level3));
        given(levelRepository.findByLevelNumber(5)).willReturn(Optional.of(level5));
        given(levelRepository.findByLevelNumber(6)).willReturn(Optional.of(level6));
        given(levelRepository.findByLevelNumber(10)).willReturn(Optional.of(level10));
        given(levelRepository.findByLevelNumber(11)).willReturn(Optional.of(level11));
        given(levelRepository.findByLevelNumber(15)).willReturn(Optional.of(level15));

        given(userService.userIdExists(any())).willReturn(false);
        given(userService.userIdExists("1")).willReturn(true);
        given(userService.userIdExists("2")).willReturn(true);
        given(userService.userIdExists("3")).willReturn(true);
        given(userService.userIdExists("5")).willReturn(true);

        given(statisticsService.getById(any())).willReturn(Optional.empty());
        given(statisticsService.getById("1")).willReturn(Optional.of(stats1));
        given(statisticsService.getById("2")).willReturn(Optional.of(stats2));
        given(statisticsService.getById("5")).willReturn(Optional.of(stats5));
        given(statisticsService.addStats("3")).willReturn(new UserStatistics("3"));

        given(program.execute(any())).willReturn(validation1);
    }

    @DisplayName(" returns two playable levels when user exists")
    @Test
    void testGetAllPlayableLevelsTwo() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("1");
        var expectedPlayableLevels = List.of(level1, level2);
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
    }

    @DisplayName(" returns three playable levels when user exists")
    @Test
    void testGetAllPlayableLevelsThree() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("5");
        var expectedPlayableLevels = List.of(level1,level2,level3);
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
    }

    @DisplayName(" returns one playable level when user exists and has stats")
    @Test
    void testGetAllPlayableLevelsOneWithStats() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("2");
        var expectedPlayableLevels = List.of(level1);
        assertEquals(expectedPlayableLevels, actualPlayableLevels, "levels don't match");
    }

    @DisplayName(" returns one playable level when user exists and has no stats")
    @Test
    void testGetAllPlayableLevelsOneWithoutStats() {
        var actualPlayableLevels = levelService.getAllPlayableLevels("3");
        var expectedPlayableLevels = List.of(level1);
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


    @DisplayName(" returns the correct level by number")
    @Test
    void testGetByLevelNumber() {
        var actualOptionalLevel = levelService.getByLevelNumber(1);
        var expectedOptionalLevel = levelRepository.findByLevelNumber(1);
        assertEquals(expectedOptionalLevel, actualOptionalLevel, "levels don't match");
    }

    @DisplayName(" returns the correct level exists by number")
    @Test
    void testLevelExists() {
        given(levelRepository.existsByLevelNumber(2)).willReturn(true);
        var actual = levelService.levelExists(2);
        assertTrue(actual, "level exists don't match");
    }

    @DisplayName("getting level number range for world null should return -1 and -1")
    @Test
    public void testGetLevelNumberRangeForWorld() {
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberAsc(any())).willReturn(Optional.of(level1));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberDesc(any())).willReturn(Optional.empty());
        var actual = levelService.getLevelNumberRangeForWorld(null);
        assertEquals(Pair.of(-1,-1),actual);
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberAsc(any())).willReturn(Optional.empty());
        actual = levelService.getLevelNumberRangeForWorld(null);
        assertEquals(Pair.of(-1,-1),actual);
    }

    public static Stream<Arguments> getLevelNumberRangeForWorldTestsArgumentProvider() {
        return Stream.of(
                arguments(inferno, Pair.of(1, 5)), // test Inferno levels
                arguments(purgatory, Pair.of(6, 10)), // test Purgatory levels
                arguments(paradise, Pair.of(11, 15)) // test Paradise levels
        );
    }

    @ParameterizedTest(name = "getting level number range for world {0} should return {1}")
    @MethodSource("getLevelNumberRangeForWorldTestsArgumentProvider")
    void getLevelNumberRangeForWorldTest(EWorld world, Pair<Integer, Integer> expected) {
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberAsc(inferno)).willReturn(Optional.of(level1));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberDesc(inferno)).willReturn(Optional.of(level5));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberAsc(purgatory)).willReturn(Optional.of(level6));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberDesc(purgatory)).willReturn(Optional.of(level10));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberAsc(paradise)).willReturn(Optional.of(level11));
        given(levelRepository.findFirstByLevelWorldOrderByLevelNumberDesc(paradise)).willReturn(Optional.of(level15));
        final Pair<Integer, Integer> actual = levelService.getLevelNumberRangeForWorld(world);
        assertEquals(expected, actual);
    }

    @DisplayName(" correctly plays a level with valid commands")
    @Test
    void testPlayLevelValid() {
        var expected = new LevelValidation();
        given(program.execute(any())).willReturn(expected);
        assertEquals(expected, levelService.playLevel(1, "1", program, ""),
                "validations don't match");
    }

    @DisplayName(" correctly plays a level with invalid commands")
    @Test
    void testPlayLevelInvalid() {
        assertFalse(levelService.playLevel(1, "1", new Program(List.of()), "").isCompleted(), "validations don't match");
    }

    @DisplayName(" correctly plays a level with exception")
    @Test
    void testPlayLevelException() {
        assertThrows(LevelInexistentException.class, () -> levelService.playLevel(20, "3",
                new Program(List.of()), ""), "should throw on no level");
        assertThrows(UserInexistentException.class, () -> levelService.playLevel(1, "4",
                new Program(List.of()), ""), "should throw on no user");
        assertThrows(UserNotAllowedException.class, () -> levelService.playLevel(10, "2",
                new Program(List.of()), ""), "should throw on high level for user");
    }
}
