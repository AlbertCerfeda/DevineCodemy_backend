package ch.usi.si.bsc.sa4.devinecodemy.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;

import java.util.Arrays;
import java.util.Optional;


/**
 *  Request router for  /auth
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;

    @Autowired
    public AuthController(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
        restTemplate = new RestTemplate();
    }

    /**
     * GET /auth/check
     * Returns whether the user is authenticated or not by returning an Optional<User>.
     * @param authenticationToken token that belongs to user.
     * @return Optional<User> user.
     *  If the user is not authenticated, returns HTTP status 401 (Unauthorized)
     */
    @GetMapping("/check")
    public ResponseEntity<Optional<User>> isAuthenticated (OAuth2AuthenticationToken authenticationToken) {
        try {
            return ResponseEntity.ok(userService.getUserByToken(authenticationToken));
        } catch (Exception ex) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * POST /auth/register
     * Registers a new user.
     * @param createUserDTO user to register.
     * @return ResponseEntity<User> user.
     * If the user is unauthenticated, returns HTTP status 401 (Unauthorized)
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody CreateUserDTO createUserDTO) {
        try {
            return ResponseEntity.ok(userService.register(createUserDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * GET /auth/logout
     * Logs out the user.
     * @param authenticationToken token that belongs to user.
     * @return ResponseEntity<Void>
     *  If the user is unauthenticated, returns HTTP status 401 (Unauthorized)
     */
    @GetMapping("/logout")
    public void logout(OAuth2AuthenticationToken authenticationToken) {
        // TODO: Implement logout
    }

    /**
     * GET /auth/gitlab
     * Redirects to the GitLab login page
     * @return RedirectView Url Redirecting to the GitLab login page
     */
    @GetMapping("/gitlab")
    public RedirectView gitlabLogin() {
        //get the client id
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("gitlab", "client");
        String clientId = authorizedClient.getClientRegistration().getClientId();
        //get the client secret
        String clientSecret = authorizedClient.getClientRegistration().getClientSecret();

        RedirectView redirectView = new RedirectView();
        // get the redirect url
        //redirectView.setUrl(String.valueOf(authorizedClient.getClientRegistration()) + "?client_id=" + clientId + "&client_secret=" + clientSecret);
        redirectView.setUrl("https://gitlab.com/oauth/authorize?client_id=" + clientId + "&client_secret=" + clientSecret);
        //redirectView.setUrl("localhost:3000/profile");
        return redirectView;
    }

    /**
     * GET /auth/gitlab/callback
     * Redirects to the GitLab login page
     * @return RedirectView Url Redirecting to the GitLab login page
     */
    @GetMapping("/gitlab/callback")
    public RedirectView gitlabCallback(@RequestParam("code") String code) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/auth/login");
        return redirectView;
    }


    /**
     * GET /auth/login
     * Creates a new user if it doesn't exist. Finally, redirects to the home page
     * @param authenticationToken Token from GitLab after the Log-in
     * @return RedirectView Url Redirecting to the home page
     */
    @GetMapping("/login")
    public RedirectView userLogin(OAuth2AuthenticationToken authenticationToken) {

        // Retrieves the user token from the GitLab Token
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName());
        if (client == null) {
            throw new IllegalArgumentException("The token is null !");
        }
        String accessToken = client.getAccessToken().getTokenValue();

        //Creates a new request to GitLab to retrieve the user data
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String plainUser;
        try {
             plainUser = restTemplate
                    .exchange("https://gitlab.com/api/v4/user?access_token=" + accessToken,
                            HttpMethod.GET,
                            entity,
                            String.class)
                    .getBody();
        } catch (Exception ex) {
            throw new RestClientException("It couldn't retrieve the user from GitLab");
        }

        //Converts the received JSON Plain text into CreateUserDTO
        ObjectMapper o = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CreateUserDTO newUser;
        try {
            newUser = o.readValue(plainUser, CreateUserDTO.class);
        } catch (Exception ex) { //If JSON received is broken, gives a Server Error
            System.out.println(ex);
            RedirectView r = new RedirectView();
            r.setUrl("/");
            return r;
        }

        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);

        // If the user does not exist yet in the database, it creates it.
        if (optionalUser.isEmpty()) {
            userService.addUser(newUser);
        }

        // For redirecting back to Home Page
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:3000/profile");
        return redirectView;
    }

}
