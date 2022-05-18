package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.ItemDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.tile.TileDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@DisplayName("The level controller")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LevelControllerTests {

    @MockBean
    private LevelService levelService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FakeOAuth2User fakeOAuth2User;
    private User user1;
    private Level level1;
    private Level level2;
    private Level level3;
    private Level level4;
    private Level level5;

    @BeforeAll
    void setup() {
        user1 = new User("id", "name", "username", "email", "avatarl",
                "bio", new SocialMedia("twitter", "skype", "linkedin"));
        fakeOAuth2User = new FakeOAuth2User(user1.getId());
        level1 = new Level("level 1", "description of 1", 1, EWorld.EARTH, 0,
                new Board(List.of(), List.of(), 0), new Robot(0, 0, EOrientation.UP),
                List.of(), "../assets/thumbnailSrc1.jpg");

        level2 = new Level("level 2", "description of 2", 2, EWorld.EARTH, 1,
                new Board(List.of(new GrassTile(1, 1, 1)), List.of(new CoinItem(1, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(EAction.COLLECT_COIN),
                "../assets/thumbnailSrc2.jpg");

        level3 = new Level("level 3", "description of 3", 3, EWorld.EARTH, 2,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(1, 2, 0)),
                        List.of(new CoinItem(1, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(EAction.MOVE_FORWARD, EAction.COLLECT_COIN),
                "../assets/thumbnailSrc3.jpg");

        level4 = new Level("level 4", "description of 4", 4, EWorld.EARTH, 4,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(2, 1, 0)),
                        List.of(new CoinItem(2, 1)), 1),
                new Robot(1, 1, EOrientation.UP), List.of(EAction.TURN_RIGHT, EAction.MOVE_FORWARD, EAction.COLLECT_COIN),
                "../assets/thumbnailSrc4.jpg");

        level5 = new Level("level 5", "description of 5", 5, EWorld.EARTH, 6,
                new Board(List.of(new GrassTile(1, 1, 0), new GrassTile(1, 2, 0),
                        new GrassTile(2, 2, 0)),
                        List.of(new CoinItem(1, 1), new CoinItem(1, 2)), 2),
                new Robot(1, 1, EOrientation.UP),
                List.of(EAction.COLLECT_COIN, EAction.MOVE_FORWARD, EAction.TURN_LEFT, EAction.TURN_RIGHT),
                "../assets/thumbnailSrc5.jpg");
    }

    @DisplayName("should be able to retrieve all the playable and unplayable levels for a user")
    @Test
    public void testGetPlayableAndUnplayableLevels() throws Exception {
        given(userService.getUserByToken(fakeOAuth2User.getOAuth2AuthenticationToken()))
                .willReturn(user1);
        given(levelService.getAllPlayableLevels("id")).willReturn(List.of(level1, level2));
        given(levelService.getAll()).willReturn(List.of(level1, level2, level3, level4, level5));
        String result = mockMvc.perform(get("/levels")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
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
        } else {
            assertEquals(expected.getBoard(),actual.getBoard(),
                    "the board of the "+first+" unplayable level is not the same as " +
                            "the board of the "+second+" level");
        }
        assertEquals(expected.getLevelWorld(), actual.getLevelWorld(),
                "the world of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the world of the " + second + " level");
        assertEquals(expected.getLevelNumber(), actual.getLevelNumber(),
                "the level number of the " + first + (playable ? " " : " un") + "playable level is not the same as " +
                        "the level number of the " + second + " level");
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
}