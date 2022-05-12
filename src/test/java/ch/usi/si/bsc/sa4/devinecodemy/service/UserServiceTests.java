package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.CreateUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.InvalidAuthTokenException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;
import ch.usi.si.bsc.sa4.devinecodemy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
        statisticsService = new StatisticsService(statisticsRepository);
        userService = new UserService(userRepository,statisticsService);

        // Setting up users
        socialMedia = new SocialMedia("a twitter", "a skype", "a linkedin");
        user = new User("an id", "a name", "a username", "an email", "an avatar", "a bio" , socialMedia);
        user1 = new User("an id1", "a name1", "a username1", "an email1", "an avatar1", "a bio1", socialMedia);
        user2 = new User("an id2", "a name2", "a username2", "an email2", "an avatar2", "a bio2", socialMedia);
    }

    @Test
    public void testGetAll() {
        List<User> users = List.of(user, user1, user2);
        given(userRepository.findAll()).willReturn(users);

        assertEquals(users, userService.getAll(), "It didn't get the list of users");
    }

    @Test
    public void testGetAllPublic() {
        user1.setPublicProfile(true);
        user2.setPublicProfile(true);
        List<User> usersPublic = List.of(user1, user2);
        given(userRepository.findAllPublic()).willReturn(usersPublic);

        assertEquals(usersPublic, userService.getAllPublic(), "It did not get public users");
    }

    @Test
    public void testIsUserPublic() {
        user.setPublicProfile(true);
        given(userRepository.isUserPublic("an id")).willReturn(Optional.of(user));

        user1.setPublicProfile(false);
        given(userRepository.isUserPublic("an id1")).willReturn(Optional.of(user1));

        assertEquals(Optional.of(true), userService.isUserPublic("an id"), "isUserPublic returns the wrong value");
        assertEquals(Optional.of(false), userService.isUserPublic("an id1"), "isUserPublic returns the wrong value");
    }

    @Test
    public void testGetById() {
        given(userRepository.findById("an id")).willReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.getById("an id"), "It didn't get the right user, given an id");
    }

    @Test
    public void testSearchByNameContaining() {
        given(userService.searchByNameContaining("a name", true)).willReturn(List.of(user));
        given(userService.searchByNameContaining("a name1", false)).willReturn(List.of(user1));

        assertEquals(List.of(user), userService.searchByNameContaining("a name", true), "It didn't get the right user list, given a name");
        assertEquals(List.of(user1), userService.searchByNameContaining("a name1", false), "It didn't get the right user list, given a name");
    }

    @Test
    public void testUserIdExists() {
        given(userRepository.existsById("an id")).willReturn(true);

        assertTrue(userService.userIdExists("an id"), "A user with the given id doesn't exists");
    }

    @Test
    public void testUserNameExists() {
        given(userRepository.existsByName("a name")).willReturn(true);

        assertTrue(userService.userNameExists("a name"), "A user with the given name doesn't exists");
    }

    @Test
    public void testAddUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("an id0", "a name0", "a username0", "an email0", "an avatar0", "a bio0", "linkedin", "twitter", "skype");
        User user0 = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),createUserDTO.getEmail(), createUserDTO.getAvatarUrl(),
                createUserDTO.getBio(), new SocialMedia(createUserDTO.getTwitter(), createUserDTO.getSkype(), createUserDTO.getLinkedin()));
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        assertDoesNotThrow(() -> {
            assertEquals(user0.getId(), userService.addUser(createUserDTO).getId(), "It didn't create the user");
        });
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        assertEquals(user, userService.updateUser(user), "It didn't update the user");
    }

    @Test
    public void testDeleteUserById() {
        userService.deleteUserById("an id");
        verify(userRepository).deleteById("an id");
    }

    @Test
    public void testCheckBodyFormat() {
        CreateUserDTO userDTO = new CreateUserDTO("an id", "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_empty = new CreateUserDTO("", "", "", "", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO UserDTO_noID = new CreateUserDTO("", "a name", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noName = new CreateUserDTO("an id", "", "a username", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noUsername = new CreateUserDTO("an id", "a name", "", "an email", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");
        CreateUserDTO userDTO_noEmail = new CreateUserDTO("an id", "a name", "a username", "", "an avatar", "a bio", "a twitter", "a skype", "a linkedin");

//        User user = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),
//                createUserDTO.getEmail(), createUserDTO.getAvatarUrl(), createUserDTO.getBio(), socialMediaUserDTO);

        assertTrue(userService.checkBodyFormat(userDTO), "The body format is correct");
        assertFalse(userService.checkBodyFormat(UserDTO_noID), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noName), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noUsername), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(userDTO_noEmail), "The body format is not incorrect");
        assertFalse(userService.checkBodyFormat(UserDTO_empty), "The body format is not incorrect");

        System.out.println("TESTING HERE:");
        System.out.println(UserService.checkBodyFormat(UserDTO_empty));

    }

    @Test
    public void testIsIdEqualToken(){
//        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken("a token", null, null);
//        token.setPrincipal("an id");
//
//        when(user.getUserByToken("a token")).thenReturn(user);
    }

    @Test
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


