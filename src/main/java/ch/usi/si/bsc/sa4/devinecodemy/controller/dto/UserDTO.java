package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;

/**
 * The stripped down state of a User object.
 */
public class UserDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private String avatar_url;
    private String bio;
    private boolean publicProfile;
    private String twitter;
    private String skype;
    private String linkedin;
    
    public UserDTO(User user) {
        this.id   = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatar_url = user.getAvatar_url();
        this.bio = user.getBio();
        this.publicProfile = user.isProfilePublic();
        this.linkedin = user.getLinkedin();
        this.skype = user.getSkype();
        this.twitter = user.getTwitter();
    }
    
    
    // Getters and setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
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
}
