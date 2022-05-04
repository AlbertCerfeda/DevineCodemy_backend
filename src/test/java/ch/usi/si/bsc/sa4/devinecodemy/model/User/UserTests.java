package ch.usi.si.bsc.sa4.devinecodemy.model.User;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The user")
public class UserTests {
    User user;
    User user2;

    @BeforeEach
    void beforeAllTests() {
        user = new User("an id", "a name", "a username", "an email", "an avatar", "a bio", "linkedin", "twitter", "skype");
        user2 = new User("another id", "another name", "another username", "another email", "an avatar", "a bio", "linkedin", "twitter", "skype");
    }

    @Test
    public void testConstructor() {
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a username", user.getUsername(), "the username field isn't set correctly");
        assertEquals("an email", user.getEmail(), "the email field isn't set correctly");
    }

    @Test
    public void testSetPublicProfile() {
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @Test
    public void testToUserDTO() {
        UserDTO userDTO = user.toPublicUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");

        UserDTO userDTO2 = user2.toPublicUserDTO();
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
    }

    @Test
    public void testToUserDTOWithPrivateProfile() {
        user.setPublicProfile(false);
        UserDTO userDTO = user.toPublicUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");
        assertEquals("a username", userDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("an email", userDTO.getEmail(), "the email field wasn't set correctly");
    }

}
