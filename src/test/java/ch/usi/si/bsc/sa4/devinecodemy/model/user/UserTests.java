package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The user")
public class UserTests {
    User user;
    User user2;
    User userPrivate;
    User userPrivate2;

    @BeforeEach
    void beforeAllTests() {
        user = new User("an id", "a name", "a username", "an email","an avatar","a bio", new SocialMedia("a twitter","a skype", "a linkedin"));
        user2 = new User("another id", "another name", "another username", "another email","another avatar","another bio", new SocialMedia("another twitter","another skype", "another linkedin"));
        userPrivate = new User("private id", "private name", "private username", "private email","private avatar","private bio", new SocialMedia( "private twitter","private skype", "private linkedin"));
        userPrivate2 = new User("private id2", "private name2", "private username2", "private email2","private avatar2","private bio2", new SocialMedia("private twitter2","private skype2", "private linkedin2"));
    }

    @Test
    public void testConstructor() {
        User user = new User("an id", "a name", "a username", "an email","an avatar","a bio", new SocialMedia("a twitter","a skype", "a linkedin"));
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a username", user.getUsername(), "the username field isn't set correctly");
        assertEquals("an email", user.getEmail(), "the email field isn't set correctly");
        assertEquals("an avatar", user.getAvatarUrl(), "the avataeUrl field isn't set correctly");
        assertEquals("a bio", user.getBio(), "the bio field isn't set correctly");
        assertEquals("a twitter", user.getSocialMedia().getTwitter(), "the twitter field isn't set correctly");
        assertEquals("a skype", user.getSocialMedia().getSkype(), "the skype field isn't set correctly");
        assertEquals("a linkedin", user.getSocialMedia().getLinkedin(), "the linkedin field isn't set correctly");
    }

    @Test
    public void testSetPublicProfile() {
        User user = new User("an id", "a name", "a username", "an email", "an avatar", "a bio", new SocialMedia("twitter", "skype", "linkedin"));
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @Test
    public void testToPublicUserDTO() {
        user.setPublicProfile(true);
        User user = new User("an id", "a name", "a username", "an email", "an avatar", "a bio", new SocialMedia("a twitter", "a skype", "a linkedin"));
        UserDTO userDTO = user.toPublicUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");
        assertEquals("a username", userDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("an email", userDTO.getEmail(), "the email field wasn't set correctly");
        assertEquals("an avatar", userDTO.getAvatarUrl(), "the avataeUrl field wasn't set correctly");
        assertEquals("a bio", userDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals("a twitter", userDTO.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("a skype", userDTO.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("a linkedin", userDTO.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue( userDTO.isVisible(), "The public profile wasn't set correctly");

        // default public profile is false
        User user2 = new User("another id", "another name", "another username", "another email", "another avatar", "another bio", new SocialMedia("another twitter", "another skype", "another linkedin"));
        UserDTO userDTO2 = user2.toPublicUserDTO();
        assertEquals("another id", userDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("another username", userDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals("another email", userDTO2.getEmail(), "the email field wasn't set correctly");
        assertEquals("another avatar", userDTO2.getAvatarUrl(), "the avataeUrl field wasn't set correctly");
        assertEquals("another bio", userDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals("another twitter", userDTO2.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("another skype", userDTO2.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("another linkedin", userDTO2.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue(userDTO2.isVisible(), "The public profile wasn't set correctly");
    }

    @Test
    public void testToPrivateUserDTO() {
        userPrivate.setPublicProfile(false);
        UserDTO privateUserDTO = new UserDTO(userPrivate, true);
        assertEquals("private id", privateUserDTO.getId(), "the id field wasn't set correctly");
        assertEquals("private name", privateUserDTO.getName(), "the name field wasn't set correctly");
        assertEquals("private username", privateUserDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("", privateUserDTO.getEmail(), "the email field wasn't set correctly. Private user doesn't share email");
        assertEquals("private avatar", privateUserDTO.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("private bio", privateUserDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals("", privateUserDTO.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly. Private user doesn't share twitter");
        assertEquals("", privateUserDTO.getSocialMedia().getSkype(), "the skype field wasn't set correctly. Private user doesn't share skype");
        assertEquals("", privateUserDTO.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly. Private user doesn't share linkedin");
        assertFalse(privateUserDTO.isVisible(), "The public profile wasn't set correctly");

        userPrivate2.setPublicProfile(false);
        UserDTO privateUserDTO2 = userPrivate2.toPrivateUserDTO();
        assertEquals("private id2", privateUserDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("private name2", privateUserDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("private username2", privateUserDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals("", privateUserDTO2.getEmail(), "the email field wasn't set correctly. Private user doesn't share email");
        assertEquals("private avatar2", privateUserDTO2.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("private bio2", privateUserDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals("", privateUserDTO2.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly. Private user doesn't share twitter");
        assertEquals("", privateUserDTO2.getSocialMedia().getSkype(), "the skype field wasn't set correctly. Private user doesn't share skype");
        assertEquals("", privateUserDTO2.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly. Private user doesn't share linkedin");
        assertFalse(privateUserDTO2.isVisible(), "The public profile wasn't set correctly");

    }
}