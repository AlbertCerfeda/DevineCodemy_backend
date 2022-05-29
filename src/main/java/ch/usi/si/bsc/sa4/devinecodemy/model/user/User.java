package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.LBUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UserDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The User class represents the user, represented with a
 * unique id and the fetched values from GitLab.
 */
@Document(collection="users")
public class User {
    @Id
    private final String id;
    private final String name;
    private final String username;
    private final String email;
    private final String avatarUrl;
    private boolean publicProfile;
    private final String bio;
    private final SocialMedia socialMedia;
    
    /**
     * Main constructor to create a User.
     * @param id ID of the user (usually retrieved from GitLab).
     * @param name name of the new user.
     * @param username username of the new user.
     * @param email email of the new user.
     */
    @PersistenceConstructor
    public User(String id, String name, String username, String email, String avatarUrl, String bio, SocialMedia socialMedia) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.socialMedia = new SocialMedia(socialMedia.getTwitter(), socialMedia.getSkype(), socialMedia.getLinkedin());
    }

    /** Retrieves the Id of a user
     * @return user's Id */
    public String getId() { return id;}

    /** Retrieves the name of a user
     * @return user's name */
    public String getName() { return name;}

    /** Retrieves the email of a user
     * @return user's email */
    public String getEmail() { return email;}

    /** Retrieves the username of a user
     * @return user's username */
    public String getUsername() { return username; }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    /** Retrieves the bio of a user.
     * @return user's bio. */
    public String getBio() { return bio;}

    /**
     * Returns whether the user profile is public or not.
     * @return whether the user profile is public or not.
     */
    public Boolean isProfilePublic() { return publicProfile;}

    /**
     * Returns the UserDTO containing ALL the user data.
     * @return the UserDTO with a public profile.
     */
    public UserDTO toPublicUserDTO() {
        return new UserDTO(this, false);
    }

    /**
     * Returns the UserDTo containing the essential data if the User is private.
     * @return the UserDTO with a private profile.
     */
    public UserDTO toPrivateUserDTO() {
        return new UserDTO(this, true);
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

    /**
     * Returns the LeaderBoard dto version of the given user with the given
     * number of completed levels.
     * @param completedLevels the number of completed levels of the user.
     * @return the LeaderBoard dto version of the given user.
     */
    public LBUserDTO toLBUserDTO(int completedLevels) { return new LBUserDTO(this, completedLevels, this.getAvatarUrl()); }

}
