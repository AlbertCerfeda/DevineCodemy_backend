package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.*;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.item.CoinItem;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.GrassTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("The level controller")
public class LevelControllerTests {

    @MockBean
    private LevelService levelService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FakeOAuth2User fakeOAuth2User1;
    private User user1;
    private User user2;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;
    private Level level5;

    @BeforeAll
    void setup() {
        user1 = new User("id", "name", "username", "email", "avatarl",
                "bio", new SocialMedia("twitter", "skype", "linkedin"));
        user2 = new User("another id", "name", "username", "email", "avatarl",
                "bio", new SocialMedia("twitter", "skype", "linkedin"));
        fakeOAuth2User1 = new FakeOAuth2User(user1.getId());
        level1 = new Level("level 1", "description of 1", 1, EWorld.INFERNO, 0,
                new Board(List.of(), List.of(), 0), new Robot(0, 0, EOrientation.UP),
                List.of(), "../assets/thumbnailSrc1.jpg");

        level2 = new Level("level 2", "description of 2", 2, EWorld.INFERNO, 1,
                new Board(List.of(new GrassTile(1, 1, 1)), List.of(new CoinItem(1, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(ECategory.BASIC_COMMANDS),
                "../assets/thumbnailSrc2.jpg");

        level3 = new Level("level 3", "description of 3", 3, EWorld.INFERNO, 2,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(1, 2, 0)),
                        List.of(new CoinItem(1, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS),
                "../assets/thumbnailSrc3.jpg");

        level4 = new Level("level 4", "description of 4", 4, EWorld.INFERNO, 4,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(2, 1, 0)),
                        List.of(new CoinItem(2, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS),
                "../assets/thumbnailSrc4.jpg");

        level5 = new Level("level 5", "description of 5", 5, EWorld.INFERNO, 6,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(1, 2, 0),
                        new GrassTile(2, 2, 0)),
                        List.of(new CoinItem(1, 1), new CoinItem(1, 2)), 2),
                new Robot(1, 1, EOrientation.UP),
                List.of(ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS, ECategory.BASIC_COMMANDS),
                "../assets/thumbnailSrc5.jpg");
    }

    @DisplayName("should be redirect when passing a null token")
    @Test
    public void testGetPlayableAndUnplayableLevelsRedirect() throws Exception {
        mockMvc.perform(get("/levels")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(null)))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("should get not found when searching for not existing user")
    @Test
    public void testGetPlayableAndUnplayableLevelsNotFound() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user2);
        given(levelService.getAllPlayableLevels("another id")).willThrow(UserInexistentException.class);
        mockMvc.perform(get("/levels")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

    @DisplayName("should be able to retrieve all the playable and unplayable levels infos for a user")
    @Test
    public void testGetPlayableAndUnplayableLevelsInfos() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getAllPlayableLevels("id")).willReturn(List.of(level1, level2));
        given(levelService.getAll()).willReturn(List.of(level1, level2, level3, level4, level5));
        String result = mockMvc.perform(get("/levels?onlyinfo=true")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn().getResponse().getContentAsString();
        Pair<List<LevelDTO>, List<LevelDTO>> actualLevels = objectMapper.readValue(result,
                new TypeReference<>() {
                });
        List<LevelDTO> playable = actualLevels.getFirst();
        List<LevelDTO> unplayable = actualLevels.getSecond();
        var firstPlayable = playable.get(0);
        var secondPlayable = playable.get(1);
        var firstUnplayable = unplayable.get(0);
        var secondUnplayable = unplayable.get(1);
        var thirdUnplayable = unplayable.get(2);
        testLevelDtoEquals(level1.toLevelInfoDTO(), firstPlayable, "first", "first", false);
        testLevelDtoEquals(level2.toLevelInfoDTO(), secondPlayable, "second", "second", false);
        testLevelDtoEquals(level3.toLevelInfoDTO(), firstUnplayable, "first", "third", false);
        testLevelDtoEquals(level4.toLevelInfoDTO(), secondUnplayable, "second", "fourth", false);
        testLevelDtoEquals(level5.toLevelInfoDTO(), thirdUnplayable, "third", "fifth", false);
    }

    @DisplayName("should be able to retrieve all the playable and unplayable levels for a user")
    @Test
    public void testGetPlayableAndUnplayableLevels() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getAllPlayableLevels("id")).willReturn(List.of(level1, level2));
        given(levelService.getAll()).willReturn(List.of(level1, level2, level3, level4, level5));
        String result = mockMvc.perform(get("/levels")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn().getResponse().getContentAsString();
        Pair<List<LevelDTO>, List<LevelDTO>> actualLevels = objectMapper.readValue(result,
                new TypeReference<>() {
                });
        List<LevelDTO> playable = actualLevels.getFirst();
        List<LevelDTO> unplayable = actualLevels.getSecond();
        var firstPlayable = playable.get(0);
        var secondPlayable = playable.get(1);
        var firstUnplayable = unplayable.get(0);
        var secondUnplayable = unplayable.get(1);
        var thirdUnplayable = unplayable.get(2);
        testLevelDtoEquals(level1.toLevelDTO(), firstPlayable, "first", "first", true);
        testLevelDtoEquals(level2.toLevelDTO(), secondPlayable, "second", "second", true);
        testLevelDtoEquals(level3.toLevelInfoDTO(), firstUnplayable, "first", "third", false);
        testLevelDtoEquals(level4.toLevelInfoDTO(), secondUnplayable, "second", "fourth", false);
        testLevelDtoEquals(level5.toLevelInfoDTO(), thirdUnplayable, "third", "fifth", false);
    }

    public void testLevelDtoEquals(LevelDTO expected, LevelDTO actual, String first, String second, boolean playable) {
        if (playable) {
            testBoardDtoEquals(expected.getBoard(), actual.getBoard(),
                    "the board of the " + first + " playable levels is not the same as " +
                            "the board of the " + second + " level");
            testRobotDtoEquals(expected.getRobot(), actual.getRobot(),
                    "the robot of the " + first + " playable levels is not the same as " +
                            "the robot of the " + second + " level");
        } else {
            assertEquals(expected.getBoard(),actual.getBoard(),
                    "the board of the "+first+" unplayable level is not the same as " +
                            "the board of the "+second+" level");
            assertEquals(expected.getRobot(),actual.getRobot(),
                    "the robot of the "+first+" unplayable level is not the same as " +
                            "the robot of the "+second+" level");
        }
        assertEquals(expected.getLevelWorld(), actual.getLevelWorld(),
                "the world of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the world of the " + second + " level");
        assertEquals(expected.getName(), actual.getName(),
                "the name of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the name number of the " + second + " level");
        assertEquals(expected.getDescription(), actual.getDescription(),
                "the description of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the description number of the " + second + " level");
        assertEquals(expected.getLevelNumber(), actual.getLevelNumber(),
                "the level number of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the level number of the " + second + " level");
        assertEquals(expected.getMaxCommandsNumber(), actual.getMaxCommandsNumber(),
                "the max commands number of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the max commands number of the " + second + " level");
        testAllowedCommandsEquals(expected.getAllowedCommands(), actual.getAllowedCommands(),
                "the allowed commands of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the allowed commands number of the " + second + " level");
        assertEquals(expected.getThumbnailSrc(), actual.getThumbnailSrc(),
                "the thumbnail source of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the thumbnail source number of the " + second + " level");
    }

    public void testAllowedCommandsEquals(List<ECategoryDTO> expected, List<ECategoryDTO> actual, String message) {
        assertEquals(expected.size(),actual.size(),
                "the size of "+message);
        for (int i = 0; i < expected.size(); i++) {
            testEActionDtoEquals(expected.get(i),actual.get(i),message);
        }
    }

    public void testEActionDtoEquals(ECategoryDTO expected, ECategoryDTO actual, String message) {
        assertEquals(expected.getDescription(),actual.getDescription(),message);
        assertEquals(expected.getName(),actual.getName(),message);
    }

    public void testRobotDtoEquals(RobotDTO expected, RobotDTO actual, String message) {
        assertEquals(expected.getPosX(), actual.getPosX(),
                "the position x of " + message);
        assertEquals(expected.getPosY(), actual.getPosY(),
                "the position y of " + message);
        assertEquals(expected.getOrientation(), actual.getOrientation(),
                "the orientation of " + message);
    }

        public void testBoardDtoEquals(BoardDTO expected, BoardDTO actual, String message) {
        assertEquals(expected.getDimX(), actual.getDimX(),
                "the dimension x of " + message);
        assertEquals(expected.getDimY(), actual.getDimY(),
                "the dimension y of " + message);
        testGridEquals(expected.getGrid(), actual.getGrid(),
                "the grid of " + message);
        testItemsEquals(expected.getItems(), actual.getItems(),
                "the items of " + message);
    }

    public void testGridEquals(List<TileDTO> expected, List<TileDTO> actual, String message) {
        assertEquals(expected.size(), actual.size(),
                "the size of " + message);
        for (int i = 0; i < expected.size(); i++) {
            testTileDtoEquals(expected.get(i), actual.get(i), message);
        }
    }

    public void testItemsEquals(List<ItemDTO> expected, List<ItemDTO> actual, String message) {
        assertEquals(expected.size(), actual.size(),
                "the size of " + message);
        for (int i = 0; i < expected.size(); i++) {
            testItemDtoEquals(expected.get(i), actual.get(i), message);
        }
    }

    public void testTileDtoEquals(TileDTO expected, TileDTO actual, String message) {
        assertEquals(expected.getPosX(),actual.getPosX(),message);
        assertEquals(expected.getPosY(),actual.getPosY(),message);
        assertEquals(expected.getPosZ(),actual.getPosZ(),message);
        assertEquals(expected.getType(),actual.getType(),message);
    }

    public void testItemDtoEquals(ItemDTO expected, ItemDTO actual, String message) {
        assertEquals(expected.getPosX(),actual.getPosX(),message);
        assertEquals(expected.getPosY(),actual.getPosY(),message);
        assertEquals(expected.getType(),actual.getType(),message);
    }

    @DisplayName("should be able to get a specific level given its number")
    @Test
    public void testGetByLevelNumber() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getByLevelNumberIfPlayable(1,"id"))
                .willReturn(Optional.of(level1));
        MvcResult result = mockMvc.perform(get("/levels/1")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();
        LevelDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                LevelDTO.class);
        testLevelDtoEquals(level1.toLevelDTO(),actual,
                "first","first",true);
    }

    @DisplayName("should be able to get a specific level info given its number and onlyinfo true as a query parameter")
    @Test
    public void testGetByLevelNumberOnlyInfo() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getByLevelNumberIfPlayable(1,"id"))
                .willReturn(Optional.of(level1));
        MvcResult result = mockMvc.perform(get("/levels/1?onlyinfo=true")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();
        LevelDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                LevelDTO.class);
        testLevelDtoEquals(level1.toLevelInfoDTO(),actual,
                "first","first",false);
    }

    @DisplayName("should not be able to get a specific level if not playable")
    @Test
    public void testGetByLevelNumberNotPlayable() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getByLevelNumberIfPlayable(1,"id"))
                .willReturn(Optional.empty());
        given(levelService.getByLevelNumber(1)).willReturn(Optional.of(level1));
        MvcResult result = mockMvc.perform(get("/levels/1?onlyinfo=true")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();
        LevelDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                LevelDTO.class);
        testLevelDtoEquals(level1.toLevelInfoDTO(),actual,
                "first","first",false);
    }

    @DisplayName("should not be able to get a specific level if user does not exist")
    @Test
    public void testGetByLevelNumberUserNotFound() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willThrow(UserInexistentException.class);
        mockMvc.perform(get("/levels/1?onlyinfo=true")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

    @DisplayName("should not be able to get a specific level if level does not exist")
    @Test
    public void testGetByLevelNumberLevelNotFound() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getByLevelNumberIfPlayable(1,"id"))
                .willThrow(LevelInexistentException.class);
        mockMvc.perform(get("/levels/1?onlyinfo=true")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

        @DisplayName("should be able to retrieve all the worlds")
    @Test
    public void testGetLevelWorlds() throws Exception {
        given(levelService.getLevelNumberRangeForWorld(EWorld.INFERNO)).willReturn(Pair.of(1,5));
        given(levelService.getLevelNumberRangeForWorld(EWorld.PURGATORY)).willReturn(Pair.of(6,10));
        given(levelService.getLevelNumberRangeForWorld(EWorld.PARADISE)).willReturn(Pair.of(11,15));
        MvcResult result = mockMvc.perform(get("/levels/worlds")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();
        List<EWorldDTO> worldList = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        List<EWorldDTO> expected = List.of(EWorld.INFERNO.toEWorldDTO(Pair.of(1,5)),
                EWorld.PURGATORY.toEWorldDTO(Pair.of(6,10)),
                EWorld.PARADISE.toEWorldDTO(Pair.of(11,15)));
        testWorldsEquals(expected,worldList,
                "the worlds don't match the inserted worlds");
    }

    public void testWorldsEquals(List<EWorldDTO> expected, List<EWorldDTO> actual,String message) {
        assertEquals(expected.size(),actual.size(),"the size of "+message);
        for (int i = 0; i < expected.size(); i++) {
            testWorldEquals(expected.get(i),actual.get(i),message);
        }
    }

    public void testWorldEquals(EWorldDTO expected, EWorldDTO actual, String message) {
        assertEquals(expected.getCongratulationsMessage(), actual.getCongratulationsMessage(),message);
        assertEquals(expected.getDescriptionMessage(), actual.getDescriptionMessage(),message);
        assertEquals(expected.getName(), actual.getName(),message);
        assertEquals(expected.getWorldNumber(), actual.getWorldNumber(),message);
        assertEquals(expected.getFirstLevelNumber(), actual.getFirstLevelNumber(),message);
        assertEquals(expected.getLastLevelNumber(), actual.getLastLevelNumber(),message);
    }

    @ParameterizedTest(name = "should be able to retrieve a specific world given its name")
    @MethodSource("getWorldArgumentsProvider")
    public void testGetWorld(EWorld world,String name, Pair<Integer,Integer> range) throws Exception {
        given(levelService.getLevelNumberRangeForWorld(world)).willReturn(range);
        MvcResult result = mockMvc.perform(get("/levels/worlds/"+name)
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                        .andReturn();
        EWorldDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                EWorldDTO.class);
        EWorldDTO expected = world.toEWorldDTO(range);
        testWorldEquals(expected,actual,
                "world does not match the world with the given name");
    }

    public static Stream<Arguments> getWorldArgumentsProvider() {
        return Stream.of(
                arguments(EWorld.INFERNO, "Purgatory", Pair.of(1,5)),
                arguments(EWorld.PURGATORY, "Paradise", Pair.of(6,10)),
                arguments(EWorld.PARADISE, "Inferno", Pair.of(11,15))
        );
    }

    @DisplayName("should not be able to retrieve a specific world given a wrong name")
    @Test
    public void testGetWorldNotFound() throws Exception {
        mockMvc.perform(get("/levels/worlds/unknownworld")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }
}