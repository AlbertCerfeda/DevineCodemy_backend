package ch.usi.si.bsc.sa4.lab02spring.model.User;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.service.PasswordHashingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The user")
public class UserTests {
    @Test
    void testConstructor() {
        User user = new User("an id", "a name", "a username", "an email");
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a username", user.getUsername(), "the username field isn't set correctly");
        assertEquals("an email", user.getEmail(), "the email field isn't set correctly");
    }

    @Test
    void testSetPublicProfile() {
        User user = new User("an id", "a name", "a username", "an email");
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @Test
    void testToUserDTO() {
        User user = new User("an id", "a name", "a username", "an email");
        UserDTO userDTO = user.toUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");

        User user2 = new User("another id", "another name", "another username", "another email");
        UserDTO userDTO2 = user2.toUserDTO();
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
    }

}