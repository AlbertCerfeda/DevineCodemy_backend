package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.User;

/**
 * The create state of a User object.
 */
public class CreateUserDTO extends GeneralUserDTO {

    private String avatar_url;

    /**
     * Default constructor. Needed by ObjectMapper.readValue() to avoid failures
     * on the base case as it doesn't know whether the JSON retrieved contains
     * the required fields.
     */
    public CreateUserDTO() {
        super();
    }

    public CreateUserDTO(String id, String name, String username, String email, String bio, String twitter, String skype, String linkedin, String avatarUrl) {
        super(id,name,username,email,bio,twitter,skype,linkedin);
        this.avatar_url = avatarUrl;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
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

    public void setBio(String bio) {
        this.bio = bio;
    }
}
