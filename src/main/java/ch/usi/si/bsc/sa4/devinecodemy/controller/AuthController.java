package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Request router for  /auth
 */
@RestController
@RequestMapping("/auth")
    public class AuthController {
    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private RestTemplate restTemplate;

    @Value("${codeland.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Autowired
    public AuthController(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
        restTemplate = new RestTemplate();
    }

    /**
     * GET /auth/check
     * Returns whether the user is authenticated or not
     * by returning a User.
     * @param authenticationToken token that belongs to user.
     * @return the User.
     * If the user is not authenticated, returns HTTP status 401 (Unauthorized)
     */

    @GetMapping("/check")
    public ResponseEntity<User> isAuthenticated(OAuth2AuthenticationToken authenticationToken) {
        try {
            return ResponseEntity.ok(userService.getUserByToken(authenticationToken));
        } catch (InvalidAuthTokenException ex) {
            return ResponseEntity.status(401).build();
        } catch (UserInexistentException e) {
            return ResponseEntity.status(404).build();
        }
    }


    /**
     * GET /auth/logout
     * Logs out the user.
     * @param authenticationToken token that belongs to user.
     */

    @GetMapping("/logout")
    public RedirectView logout(OAuth2AuthenticationToken authenticationToken) {
        final RedirectView redirectView = new RedirectView();
        redirectView.setUrl(frontendUrl + "/");
        return redirectView;
    }


    /**
     * Sets the RestTemplate to be able to mock it and test the controller.
     * This is necessary, because @InjectMocks doesn't work on the AuthController.
     * @param restTemplate The RestTemplate.
     */
    protected void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * GET /auth/login
     * Creates a new user if it doesn't exist. Finally, redirects to the home page
     * @param authenticationToken Token from GitLab after the Log-in
     * @return RedirectView Url Redirecting to the home page
     */

    @GetMapping("/login")
    public RedirectView userLogin(OAuth2AuthenticationToken authenticationToken) {
        final String accessToken = userLoginGetAccessToken(authenticationToken);

        CreateUserDTO newUser;

        try {
            newUser = userLoginFetchUserInfoFromGitLab(accessToken);
        } catch (Exception ex) { //If JSON received is broken, gives a Server Error
            Logger.getLogger(this.getClass().getName()).severe(ex.getMessage());
            RedirectView r = new RedirectView();
            r.setUrl("/");
            return r;
        }

        // If the user does not exist yet in the database, it creates it.
        userLoginAddOrUpdateUser(authenticationToken, newUser);

        // For redirecting back to Home Page
        final RedirectView redirectView = new RedirectView();
        redirectView.setUrl(frontendUrl + "/profile");
        return redirectView;
    }


    /**
     * Retrieves the user token from the GitLab Token
     * @return The user token
     * @throws IllegalArgumentException If no OAuth2AuthorizationClient could be loaded for the given OAuth2AuthenticationToken.
     */

    protected String userLoginGetAccessToken(OAuth2AuthenticationToken authenticationToken) throws IllegalArgumentException {
        final OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName());
        if (client == null) {
            throw new IllegalArgumentException("The token is null !");
        }
        return client.getAccessToken().getTokenValue();
    }


    /**
     * Creates a new request to GitLab to retrieve the user data
     * @param accessToken The access token for the user to fetch
     * @return A metadata object about the user
     */

    protected CreateUserDTO userLoginFetchUserInfoFromGitLab(String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN));
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        String plainUser;
        try {
            plainUser = restTemplate
                    .exchange("https://gitlab.com/api/v4/user?access_token=" + accessToken,
                            HttpMethod.GET,
                            entity,
                            String.class)
                    .getBody();
        } catch (HttpClientErrorException ex) {
            throw new SessionAuthenticationException(ex.getMessage());
        } catch (Exception ex) {
            throw new RestClientException(ex.getMessage());
        }

        final ObjectMapper o = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return o.readValue(plainUser, CreateUserDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't parse the JSON sent from GitLab");
        }
    }

    /**
     * Creates a new user or updates an existing one
     * @param authenticationToken The authentication token of a User
     * @param user The CreateUserDTO object containing the user data
     */
    protected void userLoginAddOrUpdateUser(OAuth2AuthenticationToken authenticationToken, CreateUserDTO user) {
        try {
            userService.getUserByToken(authenticationToken);
            userService.updateUser(
                    new User(user.getId(),
                            user.getName(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getAvatarUrl(),
                            user.getBio(),
                            new SocialMedia(
                                    user.getTwitter(),
                                    user.getSkype(),
                                    user.getLinkedin())));
        } catch (UserInexistentException e) {
            userService.addUser(user);
        }
    }
}
