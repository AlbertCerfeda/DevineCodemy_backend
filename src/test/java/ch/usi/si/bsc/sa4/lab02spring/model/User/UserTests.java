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
        User user = new User("an id", "a name", "a hash");
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a hash", user.getHash(), "the hash field isn't set correctly");

        User user2 = new User("another name", "another hash");
        assertEquals("another name", user2.getName(), "the name field isn't set correctly");
        assertEquals("another hash", user2.getHash(), "the hash field isn't set correctly");
    }

    @Test
    void testChangePassword() {
        User user = new User("a name", "");
        assertThrows(IllegalArgumentException.class, () -> {
            user.changePassword(null, "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.changePassword("", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            user.changePassword(null, null);
        });

        Throwable t = assertThrows(IllegalArgumentException.class, () -> {
            user.changePassword("actually wrong", "something new");
        });
        assertEquals("Wrong password.", t.getMessage(), "the wrong exception was thrown");

        // Generate a hash with current Spring settings
        String oldHash = PasswordHashingService.getInstance().hashPassword("oldPassword");

        User user2 = new User("another name", oldHash);
        user2.changePassword("oldPassword", "newPassword");
        assertTrue(BCrypt.checkpw("newPassword", user2.getHash()), "the new password wasn't set correctly");
    }

    @Test
    void testSetPublicProfile() {
        User user = new User("a name", "a hash");
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @Test
    void testToUserDTO() {
        User user = new User("an id", "a name", "a hash");
        UserDTO userDTO = user.toUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");

        User user2 = new User("another name", "another hash");
        UserDTO userDTO2 = user2.toUserDTO();
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
    }

}
