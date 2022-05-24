package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import ch.usi.si.bsc.sa4.devinecodemy.utils.FakeOAuth2User;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

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

    private static User user1;
    private static User user2;
    private FakeOAuth2User fakeOAuth2User;
    private FakeOAuth2User invalidOAuth2User;
    private FakeOAuth2User noAccessOAuth2User;

    private OAuth2AuthenticationToken fakeAuthenticationToken;
    private OAuth2AuthenticationToken invalidAuthenticationToken;
    private OAuth2AuthenticationToken noAccessAuthenticationToken;

    private String fakeAccessTokenValue = "someAccessToken";

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
        fakeAuthenticationToken = fakeOAuth2User.getOAuth2AuthenticationToken();
        invalidAuthenticationToken = invalidOAuth2User.getOAuth2AuthenticationToken();
        noAccessAuthenticationToken = noAccessOAuth2User.getOAuth2AuthenticationToken();

        given(this.authorizedClientService.loadAuthorizedClient(fakeAuthenticationToken.getAuthorizedClientRegistrationId(), fakeAuthenticationToken.getName())).willReturn(fakeAuthorizedClient);
        given(this.authorizedClientService.loadAuthorizedClient(invalidAuthenticationToken.getAuthorizedClientRegistrationId(), invalidAuthenticationToken.getName())).willReturn(null);
        given(this.authorizedClientService.loadAuthorizedClient(noAccessAuthenticationToken.getAuthorizedClientRegistrationId(), noAccessAuthenticationToken.getName())).willReturn(noAccessAuthorizedClient);

        given(this.fakeAccessToken.getTokenValue()).willReturn(fakeAccessTokenValue);
        given(this.noAccessAccessToken.getTokenValue()).willReturn("");

        given(this.fakeAuthorizedClient.getAccessToken()).willReturn(fakeAccessToken);
        given(this.noAccessAuthorizedClient.getAccessToken()).willReturn(noAccessAccessToken);


        // Build a
        CreateUserDTO userDto = Mockito.mock(CreateUserDTO.class);
        Mockito.when(userDto.getName()).thenReturn("fakeName");
        Mockito.when(userDto.getUsername()).thenReturn("fakeUsername");
        Mockito.when(userDto.getEmail()).thenReturn("fakeEmail");
        Mockito.when(userDto.getAvatarUrl()).thenReturn("fakeAvatarUrl");
        Mockito.when(userDto.getBio()).thenReturn("fakeBio");

        String userJson = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            userJson = mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        // Login: Mocks the RestTemplate
        authController.setRestTemplate(restTemplate);

        // Checks that RestClient errors are handled
//        Mockito.when(restTemplate.getForEntity("https://gitlab.com/api/v4/user?access_token=", String.class))
//            .thenThrow(new RestClientException("Couldn't retrieve the user from GitLab"));

        // Mocks a valid response
        Mockito.when(restTemplate
                        .exchange(Mockito.eq("https://gitlab.com/api/v4/user?access_token=" + fakeAccessTokenValue),
                                Mockito.any(),
                                Mockito.any(),
                                Mockito.eq(String.class)))
                .thenReturn(new ResponseEntity(userJson, HttpStatus.OK));

        // Mocks a broken response by omitting a few characters from the JSON
        Mockito.when(restTemplate
                        .exchange(Mockito.eq("https://gitlab.com/api/v4/user?access_token=broken"),
                                Mockito.any(),
                                Mockito.any(),
                                Mockito.eq(String.class)))
                .thenReturn(new ResponseEntity(userJson.substring(3), HttpStatus.OK));

        // Mocks an unauthorized login attempt
        Mockito.when(restTemplate
                        .exchange(Mockito.eq("https://gitlab.com/api/v4/user?access_token=invalid"),
                                Mockito.any(),
                                Mockito.any(),
                                Mockito.eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        // Mocks an unauthorized login attempt
        Mockito.when(restTemplate
                        .exchange(Mockito.eq("https://gitlab.com/api/v4/user?access_token=connection_problem"),
                                Mockito.any(),
                                Mockito.any(),
                                Mockito.eq(String.class)))
                .thenThrow(new RestClientException("i simulate a connection problem"));
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
		@Disabled // AM: fix this in another way. 
    public void testUserLogin() {
        RedirectView success = authController.userLogin(fakeAuthenticationToken);
        assertEquals("http://localhost:3000/profile", success.getUrl());

        RedirectView noAccess = authController.userLogin(noAccessAuthenticationToken);
        assertEquals("/", noAccess.getUrl());
    }


    @DisplayName("should be able to logout a user")
    @Test
    public void testUserLogout() {
        authController.logout(fakeAuthenticationToken);
    }

    @DisplayName("get user login access token")
    @Test
    public void testUserLoginGetAccessToken() {
        assertEquals(fakeAccessTokenValue, authController.userLoginGetAccessToken(fakeOAuth2User.getOAuth2AuthenticationToken()));

        assertThrows(IllegalArgumentException.class, () -> {
            authController.userLoginGetAccessToken(invalidOAuth2User.getOAuth2AuthenticationToken());
        });
    }

    @DisplayName("fetch user info from GitLab")
    @Test
    public void testUserLoginFetchUserInfoFromGitLab() {
        CreateUserDTO result = authController.userLoginFetchUserInfoFromGitLab(fakeAccessTokenValue);
        assertEquals("fakeName", result.getName());
        assertEquals("fakeUsername", result.getUsername());
        assertEquals("fakeEmail", result.getEmail());
        assertEquals("fakeAvatarUrl", result.getAvatarUrl());
        assertEquals("fakeBio", result.getBio());

        assertThrows(SessionAuthenticationException.class, () -> {
            authController.userLoginFetchUserInfoFromGitLab("invalid");
        });
        assertThrows(RestClientException.class, () -> {
            authController.userLoginFetchUserInfoFromGitLab("connection_problem");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            authController.userLoginFetchUserInfoFromGitLab("broken");
        });
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
