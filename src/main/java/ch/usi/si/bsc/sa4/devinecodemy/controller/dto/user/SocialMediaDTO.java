package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

/**
 * The SocialMediaDTO class represents the socialMedia object
 * to be used by the client.
 */
public class SocialMediaDTO {

    private final String twitter;
    private final String skype;
    private final String linkedin;

    /**
     * Constructs a new SocialMediaDTO object to be returned to the client
     * with the given values.
     * @param twitter the link to the Twitter profile.
     * @param skype the link to the Skype profile.
     * @param linkedin the link to the LinkedIn profile.
     */
    public  SocialMediaDTO(String twitter, String skype, String linkedin) {
        this.skype = skype;
        this.twitter = twitter;
        this.linkedin = linkedin;
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
