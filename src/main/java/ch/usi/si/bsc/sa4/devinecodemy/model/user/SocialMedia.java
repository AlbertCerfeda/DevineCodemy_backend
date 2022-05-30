package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.SocialMediaDTO;

/**
 * The SocialMedia class represents all the social media
 * profiles links of a certain user.
 */
public class SocialMedia {

    private final String twitter;
    private final String skype;
    private final String linkedin;

    /**
     * Constructs a new SocialMedia object with the given values.
     * @param twitter the link to the Twitter profile.
     * @param skype the Skype username.
     * @param linkedin the LinkedIn username.
     */
    public  SocialMedia(String twitter, String skype, String linkedin) {
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

    /**
     * Returns the SocialMediaDTO version of this object.
     * @return the SocialMediaDTO version of this object.
     */
    public SocialMediaDTO toSocialMediaDTO () {
        return new SocialMediaDTO(this);
    }
}
