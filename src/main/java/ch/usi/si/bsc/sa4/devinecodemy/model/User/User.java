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
    private final String email;
    private final String username;
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

    public String getAvatar_url() { return avatar_url; }

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

    public Boolean isProfilePublic() {
        return publicProfile;
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

}
