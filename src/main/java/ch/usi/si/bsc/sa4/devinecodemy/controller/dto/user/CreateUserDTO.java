package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The CreateUserDTO class represents the User object
 * on creation from GitLab.
 */
public class CreateUserDTO extends GeneralUserDTO {

    private final String avatarUrl;
    protected String twitter;
    protected String skype;
    protected String linkedin;

    /**
     * Constructs the CreateUserDTO object with the given fields.
     * @param id the id of the user.
     * @param name the name of the user.
     * @param username the username of the user.
     * @param email the email of the user.
     * @param bio the description of the user.
     * @param twitter the link to the Twitter profile of the user.
     * @param skype the link to the Skype profile of the user.
     * @param linkedin the link to the LinkedIn profile of the user.
     * @param avatarUrl the link to the profile image on GitLab of the user.
     */
    @JsonCreator
    public CreateUserDTO(@JsonProperty("id") String id,
                         @JsonProperty("name") String name,
                         @JsonProperty("username") String username,
                         @JsonProperty("email") String email,
                         @JsonProperty("bio") String bio,
                         @JsonProperty("twitter") String twitter,
                         @JsonProperty("skype") String skype,
                         @JsonProperty("linkedin") String linkedin,
                         @JsonProperty("avatar_url") String avatarUrl) {
        super(id,name,username,email,bio);
        this.avatarUrl = avatarUrl;
        this.twitter = twitter;
        this.skype = skype;
        this.linkedin = linkedin;
    }

    /**
     * Returns the link of the profile image of the user on GitLab.
     * @return the link of the profile image of the user on GitLab.
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getSkype() {
        return skype;
    }

    public String getLinkedin() {
        return linkedin;
    }
}
