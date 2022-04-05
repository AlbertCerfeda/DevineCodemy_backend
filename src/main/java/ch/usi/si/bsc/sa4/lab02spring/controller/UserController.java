package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UpdateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.Header;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.expression.ExpressionException;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  Request router for  /users
 *  Classes affected: User, UserDTO, CreateUserDTO, UpdateUserDTO
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;
    private Object Principal;
    private CreateUserDTO principal;
    private Object Authentication;

    @Autowired
    public UserController(UserService userService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.authorizedClientService = authorizedClientService;
        restTemplate = new RestTemplate();
    }

    /**
     * GET /users
     * Returns list of all public users.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        ArrayList<UserDTO> allUserDTOs = new ArrayList<UserDTO>();

        for (User user : userService.getAllPublic()) {
                allUserDTOs.add(user.toUserDTO());
        }

        return ResponseEntity.ok(allUserDTOs);
    }

    /**
     * POST /users
     * Creates a user in the database.
     * @constraint username and password cannot be null nor empty.
     * TODO: Remove password related coding
     */
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody CreateUserDTO createUserDTO,
                                     @RequestHeader(value = "accept") String accepts) {
//        if(createUserDTO.getPassword() != null && createUserDTO.getName() != null) {
            if(userService.userExists(createUserDTO.getName())) {
                return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
            } else {
                if(userService.checkBodyFormat(createUserDTO)) {
                    if (Objects.equals(accepts, "application/json") ||
                        Objects.equals(accepts, "*/*")) {
                        User savedUser = userService.createUser(createUserDTO);
                        return ResponseEntity.ok(savedUser.toUserDTO());
                    } else if (Objects.equals(accepts, "text/html")) {
                        userService.createUser(createUserDTO);
                        return ResponseEntity.ok("User created");
                    } else {
                        return new ResponseEntity<>("Change accept header", HttpStatus.NOT_ACCEPTABLE);
                    }
                } else {
                    return new ResponseEntity<>("Values of username or password cannot be empty.",
                            HttpStatus.BAD_REQUEST);
                }
            }
//        } else {
//            return new ResponseEntity<>("Both username and password must be inserted.", HttpStatus.BAD_REQUEST);
//        }
    }

    /**
     * PUT /users/:id
     * Updates the user in the database with info passed from UpdateUserDTO in RequestBody
     * The modifyProfile field of UpdateUserDTO used to indicate whether we are modifying the profile status
     * of the user or the other user attributes.
     *
     * @constraint boolean modifyProfile if true modify profile status, else modify other user fields.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id ,@RequestBody UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {
            //TODO: Check if request is sent by the authenticated user itself
            User updatedUser = optionalUser.get();

            if (updateUserDTO.isPublicProfileInitialized()) {
                updatedUser.setPublicProfile(updateUserDTO.isPublicProfile());
            }

            //TODO: other field to be modified or updated

            updatedUser = userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser.toUserDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /users/search?name=string
     * Gets the user with the specific name.
     * @constraint user's profile is public
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getByNameContaining(@RequestParam("name") String name) {
        // Same as in getAll() but with functional approach
        List<UserDTO> allUserDTOs = userService.searchByNameContaining(name, true).stream()
                .map(User::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUserDTOs);
    }
    
    /**
     * GET /users/:id
     * Gets the user with the specific id.
     * @constraint user's profile is public
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            //TODO: Check if request is sent by the authenticated user itself

            if (optionalUser.get().isProfilePublic()) {
                return ResponseEntity.ok(optionalUser.get().toUserDTO());
            } else {
                return new ResponseEntity<>("This profile is private.", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /login
     * Sets new user if it doesn't exist. Finally, redirects to the home page
     * @param authenticationToken Token from GitLab after the Log-in
     */
    @GetMapping("/login")
    public RedirectView userLogin(OAuth2AuthenticationToken authenticationToken) throws Exception {

        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName());
        if (client == null) {
            throw new RuntimeException();
        }
        String accessToken = client.getAccessToken().getTokenValue();

        System.out.println(accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.TEXT_HTML,MediaType.TEXT_PLAIN));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String plainUser = restTemplate
                .exchange("https://gitlab.com/api/v4/user?access_token=" + accessToken,
                        HttpMethod.GET,
                        entity,
                        String.class)
                .getBody();

        System.out.println("RESULT FROM GITLAB" + plainUser);

        ObjectMapper o = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CreateUserDTO newUser;
        try {
            newUser = o.readValue(plainUser, CreateUserDTO.class);
        } catch (Exception ex) {
            System.out.println(ex);
            RedirectView r = new RedirectView();
            r.setUrl("/");
            return r;
        }

        Optional<User> optionalUser = userService.getById(newUser.getId());

        if (optionalUser.isPresent()) {
            System.out.println("{'id':'"+newUser.getId() +"'}, PRESENT");
        } else {
            System.out.println("{'id':'"+newUser.getId() +"'}, MISSING");
            var added = addUser(newUser,"*/*").getBody();
            if (added.getClass() != String.class) {
                throw new Exception("Couldn't create the new user");
            } else {
                System.out.println("Response: " + added);
            }
            // TODO: Handle bad cases
        }

        // For redirecting back to Home Page
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return redirectView;
    }

}
