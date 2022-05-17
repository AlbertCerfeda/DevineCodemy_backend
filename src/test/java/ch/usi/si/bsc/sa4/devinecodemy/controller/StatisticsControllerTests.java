package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

    private FakeOAuth2User fakeOAuth2User1;
    private FakeOAuth2User fakeOAuth2User2;
    private UserStatistics stats1;
    private UserStatistics stats2;
    private User user1;
    private User user2;


    @BeforeAll
    void setup() {
        stats1 = new UserStatistics("id");
        stats2 = new UserStatistics("another id");
        user1 = new User("id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id","another name", "another username", "another email",
                "another avatar","another bio",
                new SocialMedia("another twitter","another skype", "another linkedin"));

        fakeOAuth2User1 = new FakeOAuth2User(stats1.getId());
        fakeOAuth2User2 = new FakeOAuth2User(stats2.getId());
        given(statisticsService.addStats("id")).willReturn(
                stats1);
    }

    @BeforeEach
    void setupMock() {
        given(statisticsService.getAll()).willReturn(
                List.of(stats1,stats2));
        given(statisticsService.addStats("id")).willReturn(
                stats1);
        given(userService.getUserByToken(fakeOAuth2User1.getoAuth2AuthenticationToken()))
                .willReturn(user1);
    }

    @DisplayName("should be able to retrieve the statistics of all the users")
    @Test
    public void testGetAll() throws Exception{
        given(statisticsService.getAll()).willReturn(
                List.of(stats1,stats2));
        mockMvc.perform(get("/stats")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getoAuth2AuthenticationToken())))
                .andExpect(status().isOk());
    }

    @DisplayName("should be able to retrieve the statistics of a user given its id")
    @Test
    public void testGetById() throws Exception{
        mockMvc.perform(get("/stats/id")
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(fakeOAuth2User1.getoAuth2AuthenticationToken())))
                .andExpect(status().isOk());
    }

    @DisplayName("should be able to retrieve the attempt of a user given the level number")
    @Test
    public void testGetAttempt() throws Exception{
        given(statisticsService.getAttempt("id",1,-1))
                .willReturn(List.of(EAction.MOVE_FORWARD,EAction.COLLECT_COIN));
        mockMvc.perform(get("/stats/level/1")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User1.getoAuth2AuthenticationToken())))
                .andExpect(status().isOk());
    }

    @DisplayName("should not be able to retrieve the attempt of a not existing statistics")
    @Test
    public void testGetAttemptThrowsStatisticsInexistent() throws Exception{
        given(statisticsService.getAttempt(any(),anyInt(),anyInt())).willThrow(StatisticInexistentException.class);
        given(userService.getUserByToken(fakeOAuth2User2.getoAuth2AuthenticationToken()))
                .willReturn(user2);
        mockMvc.perform(get("/stats/level/2")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User2.getoAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }

    @DisplayName("should not be able to retrieve the attempt of a not existing user")
    @Test
    public void testGetAttemptThrowsUserInexistent() throws Exception{
        given(userService.getUserByToken(fakeOAuth2User2.getoAuth2AuthenticationToken()))
                .willThrow(UserInexistentException.class);
        mockMvc.perform(get("/stats/level/2")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User2.getoAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }
}
