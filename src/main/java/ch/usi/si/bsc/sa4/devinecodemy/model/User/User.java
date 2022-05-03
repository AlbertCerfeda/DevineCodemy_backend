package ch.usi.si.bsc.sa4.devinecodemy.model.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserDTO;

@Document(collection="users")
public class User {
    @Id
    private final String id;
    private final String name;
    private final String username;
    private final String email;
    private final String avatar_url;
    private boolean publicProfile = false;
    private String bio;
    private String twitter;
    private String skype;
    private String linkedin;
    
    /**
     * Main constructor to create the User with GitLab data.
     * @param id User's id (in GitLab)
     * @param name User's name
     * @param username User's GitLab username (unique)
     * @param email User's email
     * @param avatar_url User's avatar url
     * @param bio User's bio
     * @param twitter User's twitter
     * @param skype User's skype
     * @param twitter User's linkedin
     * @param linkedin User's linkedin
     */
    @PersistenceConstructor
    public User(String id, String name, String username, String email, String avatar_url, String bio, String linkedin, String twitter, String skype) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar_url = avatar_url;
        this.bio = bio;
        this.skype = skype;
        this.twitter = twitter;
        this.linkedin = linkedin;
    }

    /** Retrieves the Id of a user
     * @return user's Id */
    public String getId() { return id;}

    /** Retrieves the name of a user
     * @return user's name */
    public String getName() { return name;}

    /** Retrieves the username of a user
     * @return user's username */
    public String getUsername() { return username; }

    /** Retrieves the email of a user
     * @return user's email */
    public String getEmail() { return email;}

    /** Retrieves the avatar url of a user
     * @return user's avatar url */
    public String getAvatar_url() { return avatar_url; }

    /** Retrieves the bio of a user
     * @return user's bio */
    public String getBio() { return bio;}

    /** Retrieves the Skype of a user, in case having a Skype account
     * @return user's Skype */
    public String getSkype() { return skype;}

    /** Retrieves the LinkedIn of a user, in case having a LinkedIn account
     * @return user's linkedIn*/
    public String getLinkedin() { return linkedin; }

    /** Retrieves the Twitter of a user, in case having a Twitter account
     * @return user's Twitter*/
    public String getTwitter() { return twitter;}

    /** Retrieves the profile status. It is true if the profile is public
     * @return true if user's Profile is public */
    public Boolean isProfilePublic() { return publicProfile;}

    /** Sets the profile status. When profile is public, it is set to true*/
    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

//    public void setBio(String bio) { this.bio = bio; }

//    public void setSkype(String skype) { this.skype = skype; }

//    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

//    public void setTwitter(String twitter) { this.twitter = twitter;}

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

}
