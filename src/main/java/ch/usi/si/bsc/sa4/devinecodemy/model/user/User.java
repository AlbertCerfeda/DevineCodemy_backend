package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.UserDTO;

/**
 * The User class represents the user, represented with a
 * unique id and the fetched values from GitLab.
 */
@Document(collection="users")
public class User {
    @Id
    private final String id;
    private final String name;
    private final String email;
    private final String username;
    private final String avatarUrl;
    private boolean publicProfile;
    private String bio;
    private SocialMedia socialMedia;
    
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
    
    

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Returns whether the user profile is public or not.
     * @return whether the user profile is public or not.
     */
    public Boolean isProfilePublic() {
        return publicProfile;
    }
    
    /**
     * Returns the UserDTO containing ALL the user data.
     * @return the UserDTO.
     */
    public UserDTO toPublicUserDTO() {
        return new UserDTO(this, false);
    }
    
    /**
     * Returns the UserDTo containing the essential data if the User is private.
     * @return the UserDTO.
     */
    public UserDTO toPrivateUserDTO() {
        return new UserDTO(this, true);
    }

    public String getBio() {
        return bio;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

}
