package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("The authentication controller")
public class AuthenticationControllerTests {
    @Mock
    private RestTemplate restTemplate;

    @MockBean
    private UserService userService;

    @MockBean
    private OAuth2AuthorizedClientService authorizedClientService;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static User user1;
    private static User user2;
    private FakeOAuth2User fakeOAuth2User;
    private FakeOAuth2User invalidOAuth2User;


    @BeforeAll
    void setup() {
        authController = new AuthController(userService, authorizedClientService);

        user1 = new User("an id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id","another name", "another username", "another email",
                "another avatar","another bio",
                new SocialMedia("another twitter","another skype", "another linkedin"));
        fakeOAuth2User = new FakeOAuth2User("garbage");
        invalidOAuth2User = new FakeOAuth2User("invalid");

        given(this.userService.getUserByToken(null))
                .willThrow(new InvalidAuthTokenException());
        given(this.userService.getUserByToken(invalidOAuth2User.getOAuth2AuthenticationToken()))
                .willThrow(new UserInexistentException());


    }

    @DisplayName("should be able to check if user is authenticated")
    @Test
    public void testIsAuthenticated() throws Exception{
        //status 200
        this.mockMvc.perform(get("/auth/check")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(fakeOAuth2User.getOAuth2AuthenticationToken())))
                .andExpect(status().isOk());

        //status 401
        this.mockMvc.perform(get("/auth/check")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(null)))
                .andExpect(status().isUnauthorized());

        //status 404
        this.mockMvc.perform(get("/auth/check")
                        .with(SecurityMockMvcRequestPostProcessors
                                .authentication(invalidOAuth2User.getOAuth2AuthenticationToken())))
                .andExpect(status().isNotFound());
    }
}
