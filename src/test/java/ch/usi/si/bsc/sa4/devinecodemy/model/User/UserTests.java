package ch.usi.si.bsc.sa4.devinecodemy.model.User;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("The user")
public class UserTests {
    User user;
    User user2;
    User userPrivate;
    User userPrivate2;

    @BeforeEach
    void beforeAllTests() {
        user = new User("an id", "a name", "a username", "an email","an avatar","a bio","a linkedin","a twitter","a skype");
        user2 = new User("another id", "another name", "another username", "another email","another avatar","another bio","another linkedin","another twitter","another skype");
        userPrivate = new User("private id", "private name", "private username", "private email","private avatar","private bio","private linkedin","private twitter","private skype");
        userPrivate2 = new User("private id2", "private name2", "private username2", "private email2","private avatar2","private bio2","private linkedin2","private twitter2","private skype2");
    }

    @Test
    public void testConstructor() {
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a username", user.getUsername(), "the username field isn't set correctly");
        assertEquals("an email", user.getEmail(), "the email field isn't set correctly");
        assertEquals("an avatar", user.getAvatar_url(), "the avatar_url field isn't set correctly");
        assertEquals("a bio", user.getBio(), "the bio field isn't set correctly");
        assertEquals("a twitter", user.getTwitter(), "the twitter field isn't set correctly");
        assertEquals("a skype", user.getSkype(), "the skype field isn't set correctly");
        assertEquals("a linkedin", user.getLinkedin(), "the linkedin field isn't set correctly");
    }

    @Test
    public void testSetPublicProfile() {
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @Test
    public void testToPublicUserDTO() {
        user.setPublicProfile(true);
        UserDTO userDTO = user.toPublicUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");
        assertEquals("a username", userDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("an email", userDTO.getEmail(), "the email field wasn't set correctly");
        assertEquals("an avatar", userDTO.getAvatar_url(), "the avatar_url field wasn't set correctly");
        assertEquals("a bio", userDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals("a twitter", userDTO.getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("a skype", userDTO.getSkype(), "the skype field wasn't set correctly");
        assertEquals("a linkedin", userDTO.getLinkedin(), "the linkedin field wasn't set correctly");
        assertEquals(true, userDTO.visible(), "The public profile wasn't set correctly");

        // default public profile is false
        UserDTO userDTO2 = user2.toPublicUserDTO();
        assertEquals("another id", userDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("another username", userDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals("another email", userDTO2.getEmail(), "the email field wasn't set correctly");
        assertEquals("another avatar", userDTO2.getAvatar_url(), "the avatar_url field wasn't set correctly");
        assertEquals("another bio", userDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals("another twitter", userDTO2.getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("another skype", userDTO2.getSkype(), "the skype field wasn't set correctly");
        assertEquals("another linkedin", userDTO2.getLinkedin(), "the linkedin field wasn't set correctly");
        assertEquals(true, userDTO2.visible(), "The public profile wasn't set correctly");
    }

    @Test
    public void testToPrivateUserDTO() {
        userPrivate.setPublicProfile(false);
        UserDTO privateUserDTO = new UserDTO(userPrivate, true);
        assertEquals("private id", privateUserDTO.getId(), "the id field wasn't set correctly");
        assertEquals("private name", privateUserDTO.getName(), "the name field wasn't set correctly");
        assertEquals("private username", privateUserDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals(null, privateUserDTO.getEmail(), "the email field wasn't set correctly. Private user doesn't share email");
        assertEquals("private avatar", privateUserDTO.getAvatar_url(), "the avatar_url field wasn't set correctly");
        assertEquals("private bio", privateUserDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals(null, privateUserDTO.getTwitter(), "the twitter field wasn't set correctly. Private user doesn't share twitter");
        assertEquals(null, privateUserDTO.getSkype(), "the skype field wasn't set correctly. Private user doesn't share skype");
        assertEquals(null, privateUserDTO.getLinkedin(), "the linkedin field wasn't set correctly. Private user doesn't share linkedin");
        assertEquals(false, privateUserDTO.visible(), "The public profile wasn't set correctly");

        userPrivate2.setPublicProfile(false);
        UserDTO privateUserDTO2 = userPrivate2.toPrivateUserDTO();
        assertEquals("private id2", privateUserDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("private name2", privateUserDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("private username2", privateUserDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals(null, privateUserDTO2.getEmail(), "the email field wasn't set correctly. Private user doesn't share email");
        assertEquals("private avatar2", privateUserDTO2.getAvatar_url(), "the avatar_url field wasn't set correctly");
        assertEquals("private bio2", privateUserDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals(null, privateUserDTO2.getTwitter(), "the twitter field wasn't set correctly. Private user doesn't share twitter");
        assertEquals(null, privateUserDTO2.getSkype(), "the skype field wasn't set correctly. Private user doesn't share skype");
        assertEquals(null, privateUserDTO2.getLinkedin(), "the linkedin field wasn't set correctly. Private user doesn't share linkedin");
        assertEquals(false, privateUserDTO2.visible(), "The public profile wasn't set correctly");

    }
}
