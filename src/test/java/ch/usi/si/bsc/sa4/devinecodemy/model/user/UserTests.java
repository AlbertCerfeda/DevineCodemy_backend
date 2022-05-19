package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UpdateUserDTO;
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
    SocialMedia socialMedia;

    @BeforeEach
    void beforeAllTests() {
        socialMedia = new SocialMedia("a twitter","a skype", "a linkedin");

        user = new User("an id", "a name", "a username", "an email","an avatar","a bio", socialMedia);
        user2 = new User("another id", "another name", "another username", "another email","another avatar","another bio", new SocialMedia("another twitter","another skype", "another linkedin"));

        userPrivate = new User("private id", "private name", "private username", "private email","private avatar","private bio", new SocialMedia( "private twitter","private skype", "private linkedin"));
        userPrivate2 = new User("private id2", "private name2", "private username2", "private email2","private avatar2","private bio2", new SocialMedia("private twitter2","private skype2", "private linkedin2"));
    }

    @DisplayName("should be able to get all the fields after creation")
    @Test
    public void testConstructor() {
        assertEquals("an id", user.getId(), "the id field isn't set correctly");
        assertEquals("a name", user.getName(), "the name field isn't set correctly");
        assertEquals("a username", user.getUsername(), "the username field isn't set correctly");
        assertEquals("an email", user.getEmail(), "the email field isn't set correctly");
        assertEquals("an avatar", user.getAvatarUrl(), "the avatarUrl field isn't set correctly");
        assertEquals("a bio", user.getBio(), "the bio field isn't set correctly");
        assertEquals("a twitter", user.getSocialMedia().getTwitter(), "the twitter field isn't set correctly");
        assertEquals("a skype", user.getSocialMedia().getSkype(), "the skype field isn't set correctly");
        assertEquals("a linkedin", user.getSocialMedia().getLinkedin(), "the linkedin field isn't set correctly");
    }

    @DisplayName("should be able to check the visibility of the user")
    @Test
    public void testIsProfilePublic(){
        assertFalse(userPrivate.isProfilePublic(), "The public profile wasn't set correctly");

        userPrivate.setPublicProfile(true);
        assertTrue(userPrivate.isProfilePublic(), "The public profile wasn't set correctly");
    }

    @DisplayName("should be able to return the public Dto of itself")
    @Test
    public void testToPublicUserDTO() {
        user.setPublicProfile(true);
        UserDTO userDTO = user.toPublicUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");
        assertEquals("a username", userDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("an email", userDTO.getEmail(), "the email field wasn't set correctly");
        assertEquals("an avatar", userDTO.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("a bio", userDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals("a twitter", userDTO.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("a skype", userDTO.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("a linkedin", userDTO.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue(userDTO.isVisible(), "The visibility wasn't set correctly");
        assertTrue(userDTO.isPublicProfile(), "The public profile wasn't set correctly");

        // default public profile is false
        UserDTO userDTO2 = user2.toPublicUserDTO();
        assertEquals("another id", userDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("another username", userDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals("another email", userDTO2.getEmail(), "the email field wasn't set correctly");
        assertEquals("another avatar", userDTO2.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("another bio", userDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals("another twitter", userDTO2.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("another skype", userDTO2.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("another linkedin", userDTO2.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue(userDTO2.isVisible(), "The visibility wasn't set correctly");
        assertFalse(userDTO2.isPublicProfile(), "The public profile wasn't set correctly");

        user.setPublicProfile(true);
        userDTO = user.toPrivateUserDTO();
        assertEquals("an id", userDTO.getId(), "the id field wasn't set correctly");
        assertEquals("a name", userDTO.getName(), "the name field wasn't set correctly");
        assertEquals("a username", userDTO.getUsername(), "the username field wasn't set correctly");
        assertEquals("an email", userDTO.getEmail(), "the email field wasn't set correctly");
        assertEquals("an avatar", userDTO.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("a bio", userDTO.getBio(), "the bio field wasn't set correctly");
        assertEquals("a twitter", userDTO.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("a skype", userDTO.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("a linkedin", userDTO.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue(userDTO.isVisible(), "The visibility wasn't set correctly");
        assertTrue(userDTO.isPublicProfile(), "The public profile wasn't set correctly");

        user2.setPublicProfile(true);
        userDTO2 = user2.toPrivateUserDTO();
        assertEquals("another id", userDTO2.getId(), "the id field wasn't set correctly");
        assertEquals("another name", userDTO2.getName(), "the name field wasn't set correctly");
        assertEquals("another username", userDTO2.getUsername(), "the username field wasn't set correctly");
        assertEquals("another email", userDTO2.getEmail(), "the email field wasn't set correctly");
        assertEquals("another avatar", userDTO2.getAvatarUrl(), "the avatarUrl field wasn't set correctly");
        assertEquals("another bio", userDTO2.getBio(), "the bio field wasn't set correctly");
        assertEquals("another twitter", userDTO2.getSocialMedia().getTwitter(), "the twitter field wasn't set correctly");
        assertEquals("another skype", userDTO2.getSocialMedia().getSkype(), "the skype field wasn't set correctly");
        assertEquals("another linkedin", userDTO2.getSocialMedia().getLinkedin(), "the linkedin field wasn't set correctly");
        assertTrue(userDTO2.isVisible(), "The visibility wasn't set correctly");
        assertTrue(userDTO2.isPublicProfile(), "The public profile wasn't set correctly");
    }

    @DisplayName("should be able to return the private dto of itself")
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
        assertFalse(privateUserDTO.isVisible(), "The visibility wasn't set correctly");
        assertFalse(privateUserDTO.isPublicProfile(), "The public profile wasn't set correctly");

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
        assertFalse(privateUserDTO2.isVisible(), "The visibility wasn't set correctly");
        assertFalse(privateUserDTO2.isPublicProfile(), "The public profile wasn't set correctly");
    }

    @DisplayName("should be able to return the update dto of itself")
    @Test
    public void testUpdateUserDTO() {
        UpdateUserDTO userDTO = new UpdateUserDTO(user.getId(),true,user.isProfilePublic());
        assertEquals(user.isProfilePublic(),userDTO.isPublicProfile(),
                "public profile of the update dto does not match the one of the user");
        assertEquals(user.getId(),userDTO.getId(),
                "id of the update dto does not match the one of the user");
        assertTrue(userDTO.isPublicProfileInitialized(),
                "public profile initialized does not match the one given in the constructor");
        UpdateUserDTO userDTO1 = new UpdateUserDTO();
        assertNull(userDTO1.getId(),
                "id is not null after creation without parameters");
        assertFalse(userDTO1.isPublicProfile(),
                "public profile is not false after creation without parameters");
        assertFalse(userDTO1.isPublicProfileInitialized(),
                "public profile initialized status is not false after creation without parameters");
    }

    @DisplayName("should be able to set the visibility to the given value")
    @Test
    public void testSetPublicProfile() {
        user.setPublicProfile(true);
        assertEquals(true, user.isProfilePublic(), "The public profile wasn't set correctly");

        user.setPublicProfile(false);
        assertEquals(false, user.isProfilePublic(), "The public profile wasn't set correctly");
    }
}
