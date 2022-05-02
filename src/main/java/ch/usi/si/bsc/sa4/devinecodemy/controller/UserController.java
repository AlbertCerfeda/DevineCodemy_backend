package ch.usi.si.bsc.sa4.devinecodemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UpdateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Request router for  /users
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final StatisticsService statisticsService;
    private OAuth2AuthorizedClientService authorizedClientService;
    private final RestTemplate restTemplate;

    @Autowired
    public UserController(UserService userService, StatisticsService statisticsService, OAuth2AuthorizedClientService authorizedClientService) {
        this.userService = userService;
        this.statisticsService = statisticsService;
        this.authorizedClientService = authorizedClientService;
        restTemplate = new RestTemplate();
    }

    /**
     * GET /users
     * Returns list of all public users.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAllPublic().stream().map(User::toPublicUserDTO).collect(Collectors.toList()));
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
    public ResponseEntity<?> updateUser(OAuth2AuthenticationToken authenticationToken, @PathVariable String id ,@RequestBody UpdateUserDTO updateUserDTO) throws IllegalArgumentException {
        Optional<User> optionalUser = userService.getById(id);
        
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        User updatedUser = optionalUser.get();

        if (!userService.isIdEqualToken(authenticationToken,id)) {
            throw new IllegalArgumentException("Authenticated user does not match user in PUT request.");
        }

        if (updateUserDTO.isPublicProfileInitialized()) {
            updatedUser.setPublicProfile(updateUserDTO.isPublicProfile());
        }


        updatedUser = userService.updateUser(updatedUser);
        return ResponseEntity.ok(updatedUser.toPublicUserDTO());

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
                .map(User::toPublicUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUserDTOs);
    }
    
    /**
     * GET /users/:id
     * Gets the user with the specific id only if it's public or the user making the request.
     * @constraint user's profile is public or of the user itself
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(OAuth2AuthenticationToken authenticationToken, @PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getById(id);
        
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        if (userService.isIdEqualToken(authenticationToken,id) || optionalUser.get().isProfilePublic()) {
            return ResponseEntity.ok(optionalUser.get().toPublicUserDTO());
        } else {
            return ResponseEntity.ok(optionalUser.get().toPrivateUserDTO());
        }
    }
}