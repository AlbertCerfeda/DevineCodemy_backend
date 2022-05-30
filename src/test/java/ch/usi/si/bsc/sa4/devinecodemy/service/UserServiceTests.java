package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserAlreadyExistsException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import ch.usi.si.bsc.sa4.devinecodemy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UserServiceTests {
    // Mocks
    UserRepository userRepository;
    StatisticsRepository statisticsRepository;
    UserService userService;
    StatisticsService statisticsService;

    // Variables related to users
    SocialMedia socialMedia;
    User user;
    User user1;
    User user2;

    @BeforeEach
    void beforeAllTests() {
        //Mocking repositories
        userRepository = mock(UserRepository.class);
        statisticsRepository = mock(StatisticsRepository.class);

        // Statistics service
        statisticsService = new StatisticsService(statisticsRepository, userRepository);
        userService = new UserService(userRepository,statisticsService);

        // Setting up users
        socialMedia = new SocialMedia("a twitter", "a skype", "a linkedin");
        user = new User("an id", "a name", "a username", "an email", "an avatar", "a bio" , socialMedia);
        user1 = new User("an id1", "a name1", "a username1", "an email1", "an avatar1", "a bio1", socialMedia);
        user2 = new User("an id2", "a name2", "a username2", "an email2", "an avatar2", "a bio2", socialMedia);
    }

    @Test
    @DisplayName("Test get list of all users")
    public void testGetAll() {
        List<User> users = List.of(user, user1, user2);
        given(userRepository.findAll()).willReturn(users);

        assertEquals(users, userService.getAll(), "It didn't get the list of users");
    }

    @Test
    @DisplayName("Test get list of all public users")
    public void testGetAllPublic() {
        user1.setPublicProfile(true);
        user2.setPublicProfile(true);
        List<User> usersPublic = List.of(user1, user2);
        given(userRepository.findAllPublic()).willReturn(usersPublic);

        assertEquals(usersPublic, userService.getAllPublic(), "It did not get public users");
    }

    @Test
    @DisplayName("Test if the user is public")
    public void testIsUserPublic() {
        user.setPublicProfile(true);
        given(userRepository.isUserPublic("an id")).willReturn(Optional.of(user));

        user1.setPublicProfile(false);
        given(userRepository.isUserPublic("an id1")).willReturn(Optional.of(user1));

        assertEquals(Optional.of(true), userService.isUserPublic("an id"), "isUserPublic returns the wrong value");
        assertEquals(Optional.of(false), userService.isUserPublic("an id1"), "isUserPublic returns the wrong value");
    }

    @Test
    @DisplayName("Test get user by id")
    public void testGetById() {
        given(userRepository.findById("an id")).willReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.getById("an id"), "It didn't get the right user, given an id");
    }

    @Test
    @DisplayName("Test search by name containing a specific string")
    public void testSearchByNameContaining() {
        given(userService.searchByNameContaining("a name", true)).willReturn(List.of(user));
        given(userService.searchByNameContaining("a name1", false)).willReturn(List.of(user1));

        assertEquals(List.of(user), userService.searchByNameContaining("a name", true), "It didn't get the right user list, given a name");
        assertEquals(List.of(user1), userService.searchByNameContaining("a name1", false), "It didn't get the right user list, given a name");
    }

    @Test
    @DisplayName("Test if the user's id exists")
    public void testUserIdExists() {
        given(userRepository.existsById("an id")).willReturn(true);

        assertTrue(userService.userIdExists("an id"), "A user with the given id doesn't exists");
    }

    @Test
    @DisplayName("Test if the user's name exists")
    public void testUserNameExists() {
        given(userRepository.existsByName("a name")).willReturn(true);

        assertTrue(userService.userNameExists("a name"), "A user with the given name doesn't exists");
    }

    @Test
    @DisplayName("Test adding a user")
    public void testAddUser() {
        CreateUserDTO userDTO = new CreateUserDTO("an id", "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_empty = new CreateUserDTO("", "", "", "", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_noID = new CreateUserDTO(null, "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noName = new CreateUserDTO("an id", null, "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noUsername = new CreateUserDTO("an id", "a name", null, "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");

        User user_complete = new User(userDTO.getId(),userDTO.getName(),userDTO.getUsername(),userDTO.getEmail(), userDTO.getAvatarUrl(),
                userDTO.getBio(), new SocialMedia(userDTO.getTwitter(), userDTO.getSkype(), userDTO.getLinkedin()));
        User user_empty = new User(userDTO.getId(),userDTO.getName(),userDTO.getUsername(),userDTO.getEmail(), userDTO.getAvatarUrl(),
                userDTO.getBio(), new SocialMedia(userDTO.getTwitter(), userDTO.getSkype(), userDTO.getLinkedin()));
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        assertDoesNotThrow(() -> {
            assertEquals(user_complete.getId(), userService.addUser(userDTO).getId(), "It didn't create the user");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(UserDTO_empty);
        }   , "It created a user with empty fields");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(UserDTO_noID);
        }   , "It created a user with no id");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(userDTO_noName);
        }   , "It created a user with no name");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(userDTO_noUsername);
        }   , "It created a user with no username");

        given(userRepository.existsById(userDTO.getId())).willReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.addUser(userDTO);
        }  , "It created a user with an id that already exists");

    }

    @Test
    @DisplayName("Test updating a user")
    public void testUpdateUser() {
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        assertEquals(user, userService.updateUser(user), "It didn't update the user");
    }

    @Test
    @DisplayName("Test deleting a user")
    public void testDeleteUserById() {
        userService.deleteUserById("an id");
        verify(userRepository).deleteById("an id");
    }

    @Test
    @DisplayName("Test if the user's body-format is correct")
    public void testCheckBodyFormat() {
        CreateUserDTO userDTO = new CreateUserDTO("an id", "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_empty = new CreateUserDTO("", "", "", "", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_noID = new CreateUserDTO("", "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noName = new CreateUserDTO("an id", "", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noUsername = new CreateUserDTO("an id", "a name", "", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noEmail = new CreateUserDTO("an id", "a name", "a username", "", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");

        assertTrue(userService.checkBodyFormat(userDTO), "The body format is correct");
        assertFalse(userService.checkBodyFormat(UserDTO_noID), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noName), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noUsername), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noEmail), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(UserDTO_empty), "The body format is not incorrect");
    }

    @Test
    @DisplayName("Test if the user's id is the same as the user's token")
    public void testIsIdEqualToken(){
        OAuth2AuthenticationToken token = mock(OAuth2AuthenticationToken.class);
        OAuth2AuthenticationToken tokenInvalid = mock(OAuth2AuthenticationToken.class);
        OAuth2User oAuth2User = mock(OAuth2User.class);
        OAuth2User oAuth2UserInvalid = mock(OAuth2User.class);

        given(oAuth2User.getName()).willReturn("an id");
        given(oAuth2UserInvalid.getName()).willReturn("an invalid id");
        given(token.getPrincipal()).willReturn(oAuth2User);
        given(tokenInvalid.getPrincipal()).willReturn(oAuth2UserInvalid);
        given(userRepository.findById("an id")).willReturn(Optional.of(user));

        assertFalse(userService.isIdEqualToken(null,"something"));
        assertFalse(userService.isIdEqualToken(tokenInvalid, "something"));
        assertTrue(userService.isIdEqualToken(token, "an id"));
    }

    @Test
    @DisplayName("Test get user when given a token")
    public void testGetUserByToken() {
        OAuth2AuthenticationToken token = mock(OAuth2AuthenticationToken.class);
        OAuth2AuthenticationToken tokenInvalid = mock(OAuth2AuthenticationToken.class);
        OAuth2AuthenticationToken tokenNull = null;
        OAuth2User oAuth2User = mock(OAuth2User.class);
        OAuth2User oAuth2UserInvalid = mock(OAuth2User.class);
        given(oAuth2User.getName()).willReturn("an id");
        given(oAuth2UserInvalid.getName()).willReturn("an invalid id");
        given(token.getPrincipal()).willReturn(oAuth2User);
        given(tokenInvalid.getPrincipal()).willReturn(oAuth2UserInvalid);
        given(userRepository.findById("an id")).willReturn(Optional.of(user));

        assertThrows(UserInexistentException.class, () -> userService.getUserByToken(tokenInvalid), "Exception has been thrown: The user does not exist");

        assertThrows(InvalidAuthTokenException.class, () -> userService.getUserByToken(tokenNull), "Exception has been thrown: The token is null");

        assertEquals(user, userService.getUserByToken(token), "Given a token, the user has not been returned");
    }
}


