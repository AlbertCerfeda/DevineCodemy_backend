package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    private FakeOAuth2User noAccessOAuth2User;

    @Mock
    private OAuth2AuthorizedClient fakeAuthorizedClient;

    @Mock
    private OAuth2AuthorizedClient noAccessAuthorizedClient;

    @Mock
    private OAuth2AccessToken fakeAccessToken;
    @Mock
    private OAuth2AccessToken noAccessAccessToken;

    @BeforeEach
    void setup() {
        authController = new AuthController(userService, authorizedClientService);


        user1 = new User("an id","a name", "a username", "an email","an avatar",
                "a bio",new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id","another name", "another username", "another email",
                "another avatar","another bio",
                new SocialMedia("another twitter","another skype", "another linkedin"));
        fakeOAuth2User = new FakeOAuth2User("garbage");
        invalidOAuth2User = new FakeOAuth2User("invalid");
        noAccessOAuth2User = new FakeOAuth2User("no access");

        given(this.userService.getUserByToken(null))
                .willThrow(new InvalidAuthTokenException());
        given(this.userService.getUserByToken(invalidOAuth2User.getOAuth2AuthenticationToken()))
                .willThrow(new UserInexistentException());

        /// Login: Mocks for making login test work
        String fakeAccessTokenValue = "TODO";
        OAuth2AuthenticationToken fakeToken = fakeOAuth2User.getOAuth2AuthenticationToken();
        OAuth2AuthenticationToken invalidToken = invalidOAuth2User.getOAuth2AuthenticationToken();
        OAuth2AuthenticationToken noAccessToken = noAccessOAuth2User.getOAuth2AuthenticationToken();
        given(this.authorizedClientService.loadAuthorizedClient(fakeToken.getAuthorizedClientRegistrationId(), fakeToken.getName())).willReturn(fakeAuthorizedClient);
        given(this.authorizedClientService.loadAuthorizedClient(invalidToken.getAuthorizedClientRegistrationId(), invalidToken.getName())).willReturn(null);
        given(this.authorizedClientService.loadAuthorizedClient(noAccessToken.getAuthorizedClientRegistrationId(), noAccessToken.getName())).willReturn(noAccessAuthorizedClient);
        given(this.fakeAuthorizedClient.getAccessToken()).willReturn(fakeAccessToken);
        given(this.fakeAccessToken.getTokenValue()).willReturn(fakeAccessTokenValue);
        given(this.noAccessAuthorizedClient.getAccessToken()).willReturn(noAccessAccessToken);
        given(this.noAccessAccessToken.getTokenValue()).willReturn("");


        // Login: Mocks the RestTemplate
        String response = "";

        // Checks that RestClient errors are handled
        Mockito.when(restTemplate.getForEntity("https://gitlab.com/api/v4/user?access_token=", String.class))
            .thenThrow(new RestClientException("Couldn't retrieve the user from GitLab"));

        // Mocks a valid response
        Mockito.when(restTemplate
                .exchange(Mockito.eq("https://gitlab.com/api/v4/user?access_token=" + fakeAccessTokenValue),
                        Mockito.any(),
                        Mockito.any(),
                        Mockito.eq(String.class)))
            .thenReturn(new ResponseEntity(response, HttpStatus.OK));
        Mockito.when(restTemplate.getForEntity("https://gitlab.com/api/v4/user?access_token=" + fakeAccessTokenValue, String.class))
            .thenReturn(new ResponseEntity(response, HttpStatus.OK));
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

    @DisplayName("should be able to log in a user")
    @Test
    public void testUserLogin() {

    }

    @DisplayName("get user login access token")
    @Test
    public void testUserLoginGetAccessToken() {
        assertEquals("TODO", authController.userLoginGetAccessToken(fakeOAuth2User.getOAuth2AuthenticationToken()));

        assertThrows(IllegalArgumentException.class, () -> {
            authController.userLoginGetAccessToken(invalidOAuth2User.getOAuth2AuthenticationToken());
        });
    }

    @DisplayName("fetch user info from GitLab")
    @Test
    public void testUserLoginFetchUserInfoFromGitLab() {

//        assertThrows(RestClientException.class, () -> {
//            authController.userLoginFetchUserInfoFromGitLab("TODO");
//        });
    }

    @DisplayName("add or update a user")
    @Test
    public void testUserLoginAddOrUpdateUser() {
        CreateUserDTO fakeUser = Mockito.mock(CreateUserDTO.class);
        authController.userLoginAddOrUpdateUser(fakeOAuth2User.getOAuth2AuthenticationToken(), fakeUser);
        verify(userService).getUserByToken(fakeOAuth2User.getOAuth2AuthenticationToken());
        verify(userService).updateUser(Mockito.any());

        authController.userLoginAddOrUpdateUser(invalidOAuth2User.getOAuth2AuthenticationToken(), fakeUser);
        verify(userService).addUser(Mockito.any());
    }
}
