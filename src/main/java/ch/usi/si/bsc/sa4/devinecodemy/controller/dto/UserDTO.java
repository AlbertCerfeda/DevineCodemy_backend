package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;

/**
 * The stripped down state of a User object.
 */
public class UserDTO {
    // The visible variable indicates whether the data about the user has been hidden (eg if its private)
    private boolean visible;
    private String id;
    private String name;
    private String username;
    private String email;
    private String avatarUrl;
    private String bio;
    private boolean publicProfile;
    private String twitter;
    private String skype;
    private String linkedin;
    
    /**
     * Constructor of UserDTO.
     * @param user the User from which to retrieve the DTO data.
     * @param checkPrivate if true keeps only the essential data if the profile is private.
     */
    public UserDTO(User user, boolean checkPrivate) {
        this.id   = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatar_url();
        this.bio = user.getBio();
        this.publicProfile = user.isProfilePublic();
        this.linkedin = user.getLinkedin();
        this.skype = user.getSkype();
        this.twitter = user.getTwitter();
        this.visible = true;
        
        if(checkPrivate && !user.isProfilePublic()) {
            this.email = "";
            this.linkedin = "";
            this.skype = "";
            this.twitter = "";
            this.visible = false;
        }
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

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public boolean isVisible() { return this.visible; }

    public void setVisible(boolean visible) { this.visible = visible; }
}
