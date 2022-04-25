package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UpdateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class UserServiceTests {
    UserRepository userRepository;
    UserService userService;
    User user;
    User user1;
    User user2;

    @BeforeEach
    void beforeAllTests() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        user = new User("an id", "a name", "a username", "an email");
        user1 = new User("an id1", "a name1", "a username1", "an email1");
        user2 = new User("an id2", "a name2", "a username2", "an email2");
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

        assertEquals(Optional.of(true), userService.isUserPublic("an id"), "isUserPublic returns the wrong value");
    }

    @Test
    public void testGetById() {
        given(userRepository.findById("an id")).willReturn(Optional.of(user));

        assertEquals(Optional.of(user), userService.getById("an id"), "It didn't get the right user, given an id");
    }

    @Test
    public void testSearchByNameContaining() {
        given(userService.searchByNameContaining("a name", true)).willReturn(List.of(user));
        given(userService.searchByNameContaining("a name1", false)).willReturn(List.of(user2));

        assertEquals(List.of(user), userService.searchByNameContaining("a name", true), "It didn't get the right users");
        assertEquals(List.of(user2), userService.searchByNameContaining("a name1", false), "It didn't get the right users");
    }

    @Test
    public void testUserExists() {
        given(userRepository.existsByName("a name")).willReturn(true);

        assertTrue(userService.userExists("a name"), "A user with the given name doesn't exists");
    }

    @Test
    public void testCreateUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO("an id0", "a name0", "a username0", "an email0");
        User user0 = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),createUserDTO.getEmail());
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        User answer = userService.createUser(createUserDTO);

        assertEquals(user0.getId(), answer.getId(), "It didn't create the user");
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        assertEquals(user, userService.updateUser(user), "It didn't update the user");
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUserById("an id");
        verify(userRepository).deleteById("an id");
    }

    @Test
    public void testcheckBodyFormat() {
        CreateUserDTO createUserDTO = new CreateUserDTO("", "a name", "a username", "an email");
        CreateUserDTO createUserDTO0 = new CreateUserDTO("an id0", "a name0", "a username0", "an email0");

        User user = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),createUserDTO.getEmail());
        User user0 = new User(createUserDTO.getId(),createUserDTO.getName(),createUserDTO.getUsername(),createUserDTO.getEmail());

        assertEquals(true, userService.checkBodyFormat(createUserDTO0), "The body format is correct");
        assertEquals(false, userService.checkBodyFormat(createUserDTO), "The body format is not incorrect");
    }
}
