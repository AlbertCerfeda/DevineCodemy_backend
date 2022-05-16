package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.DevineCodemyBackend;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DevineCodemyBackend.class)
@ContextConfiguration(classes = DevineCodemyBackend.class)
@DisplayName("The Play controller")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatisticsControllerUserTests {

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FakeOAuth2User fakeOAuth2User;
    private UserStatistics stats1;
    private UserStatistics stats2;
    private User user1;

    @BeforeAll
    void setup() {
        stats1 = new UserStatistics("id");
        stats2 = new UserStatistics("another id");
        user1 = new User("an id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));

        fakeOAuth2User = new FakeOAuth2User(stats1.getId());
        given(statisticsService.getAll()).willReturn(
                List.of(stats1,stats2));
        given(statisticsService.addStats("id")).willReturn(
                stats1);
        given(userService.getUserByToken(any()))
                .willReturn(user1);
    }

    @DisplayName("should be able to retrieve the attempt of a user given the level number")
    @Test
    public void testGetAttempt() throws Exception{
        mockMvc.perform(get("/stats/level/1")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getoAuth2AuthenticationToken())))
                .andExpect(status().isOk());
    }
}
