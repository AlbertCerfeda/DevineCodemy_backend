package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.PlayLevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@DisplayName("The Play controller")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayControllerTests {

    @MockBean
    private LevelService levelService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private FakeOAuth2User fakeOAuth2User;
    private PlayLevelDTO playLevelDTO;

    @BeforeAll
    void setup() {
        playLevelDTO = new PlayLevelDTO(1,
                List.of("turnRight","moveForward","turnLeft","moveForward","collectCoin"));
        User user1 = new User("an id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        fakeOAuth2User = new FakeOAuth2User("an id");
        LevelValidation levelValidation = mock(LevelValidation.class);
        LevelValidationDTO levelValidationDTO = mock(LevelValidationDTO.class);
        given(levelValidation.toLevelValidationDTO()).willReturn(levelValidationDTO);
        given(userService.getUserByToken(fakeOAuth2User.getoAuth2AuthenticationToken())).willReturn(user1);
        given(levelService.playLevel(1,"an id",
                List.of("turnRight","moveForward","turnLeft","moveForward","collectCoin")))
                .willReturn(levelValidation);
    }

    @DisplayName("should be able to play a level given a proper body")
    @Test
    public void testPlay() throws Exception {
        String body = objectMapper.writeValueAsString(playLevelDTO);
        mockMvc.perform(put("/play/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                .with(SecurityMockMvcRequestPostProcessors
                        .authentication(fakeOAuth2User.getoAuth2AuthenticationToken())))
                .andExpect(status().isOk());
    }
}
