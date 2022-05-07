package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

import ch.usi.si.bsc.sa4.devinecodemy.model.user.SocialMedia;

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
     */
    public  SocialMediaDTO() {
        this.twitter = "";
        this.skype = "";
        this.linkedin = "";
    }

    /**
     * Constructs a new SocialMediaDTO object to be returned to the client
     * with the given values.
     * @param socialMedia the socialMedia from which to retrieve the data.
     */
    public  SocialMediaDTO(SocialMedia socialMedia) {
        this.skype = socialMedia.getSkype();
        this.twitter = socialMedia.getTwitter();
        this.linkedin = socialMedia.getLinkedin();
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
