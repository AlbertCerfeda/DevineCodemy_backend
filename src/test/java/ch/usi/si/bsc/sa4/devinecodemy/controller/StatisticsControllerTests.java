package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.DynamicJsonObject;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@DisplayName("The Play controller")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatisticsControllerTests {

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FakeOAuth2User fakeOAuth2User1;
    private FakeOAuth2User fakeOAuth2User2;
    private UserStatistics stats1;
    private UserStatistics stats2;
    private User user1;
    private User user2;
    private String attempt;


    @BeforeAll
    void setup() {
        attempt = "";

        stats1 = new UserStatistics("id");
        stats2 = new UserStatistics("another id");

        user1 = new User("id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id","another name", "another username", "another email",
                "another avatar","another bio",
                new SocialMedia("another twitter","another skype", "another linkedin"));

        fakeOAuth2User1 = new FakeOAuth2User(stats1.getId());
        fakeOAuth2User2 = new FakeOAuth2User(stats2.getId());

        given(statisticsService.addStats("id")).willReturn(stats1);
    }

    @BeforeEach
    void setupMock() {
        given(statisticsService.getAll()).willReturn(
                List.of(stats1,stats2));

        given(statisticsService.addStats("id")).willReturn(
                stats1);

        given(userService.getUserByToken(fakeOAuth2User1.getOAuth2AuthenticationToken()))
                .willReturn(user1);
    }

    @Test
    @DisplayName("should be able to retrieve the statistics of all the users")
    public void testGetAll() throws Exception{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        given(statisticsService.getAll()).willReturn(
                List.of(stats1,stats2));

        MvcResult result = mockMvc.perform(get("/stats")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();

        List<UserStatisticsDTO> statisticsDTOS = objectMapper
                .readValue(result.getResponse().getContentAsString(),
                        new TypeReference<List<UserStatisticsDTO>>(){});

        assertEquals(stats1.getId(),statisticsDTOS.get(0).getId(),
                "first id of the list is not the id of the first statistics");

        assertEquals(stats1.getData(),statisticsDTOS.get(0).getData(),
                "first data of the list is not the data of the first statistics");

        assertEquals(stats2.getId(),statisticsDTOS.get(1).getId(),
                "second id of the list is not the id of the second statistics");

        assertEquals(stats2.getData(),statisticsDTOS.get(1).getData(),
                "second data of the list is not the data of the second statistics");
    }


    @Test
    @DisplayName("should be able to retrieve the statistics of a user given its id")
    public void testGetById() throws Exception{
        MvcResult result = mockMvc.perform(get("/stats/id")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();

        UserStatisticsDTO statisticsDTO = objectMapper
                .readValue(result.getResponse().getContentAsString(),UserStatisticsDTO.class);

        assertEquals(stats1.getId(),statisticsDTO.getId(),
                "id of the returned statistics doesn't match the given one");
    }

    @Test
    @DisplayName("should be able to retrieve the attempt of a user given the level number")
    public void testGetAttempt() throws Exception{
        given(statisticsService.getAttempt("id",1,-1))
                .willReturn(attempt);

        MvcResult result = mockMvc.perform(get("/stats/level/1")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();

        String actual = result.getResponse().getContentAsString();

        assertEquals(attempt,actual,
                "description of the first command of the returned attempt" +
                        "doesn't match the given one");

        assertEquals(attempt,actual,
                "name of the first command of the returned attempt" +
                        "doesn't match the given one");
    }

    @Test
    @DisplayName("should not be able to retrieve the attempt of a not existing statistics")
    public void testGetAttemptThrowsStatisticsInexistent() throws Exception{
        given(statisticsService.getAttempt(any(),anyInt(),anyInt())).willThrow(StatisticInexistentException.class);

        given(userService.getUserByToken(fakeOAuth2User2.getOAuth2AuthenticationToken()))
                .willReturn(user2);

        mockMvc.perform(get("/stats/level/2")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User2.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should not be able to retrieve the attempt of a not existing user")
    public void testGetAttemptThrowsUserInexistent() throws Exception{
        given(userService.getUserByToken(fakeOAuth2User2.getOAuth2AuthenticationToken()))
                .willThrow(UserInexistentException.class);

        mockMvc.perform(get("/stats/level/2")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User2.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should be able to retrieve the list of users for the leaderboard")
    public void testGetLBUsers() throws Exception{
        var userLBDto1 = user1.toLBUserDTO(2);
        var userLBDto2 = user2.toLBUserDTO(1);

        given(statisticsService.sortedLeaderboardUsers()).willReturn(List.of(userLBDto1,userLBDto2));

        MvcResult result = mockMvc.perform(get("/stats/leaderboard")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getOAuth2AuthenticationToken())))
                .andReturn();

        final String plainResult = result.getResponse().getContentAsString();

        List<DynamicJsonObject> users = objectMapper.readValue(plainResult,
                new TypeReference<>() {});

        assertEquals(userLBDto1.getAvatarUrl(),users.get(0).get("avatarUrl"),
                "the first user of the LeaderBoard is not the one with most completed levels");

        assertEquals(userLBDto2.getAvatarUrl(),users.get(1).get("avatarUrl"),
                "the last user of the LeaderBoard is not the one with less completed levels");
    }
}
