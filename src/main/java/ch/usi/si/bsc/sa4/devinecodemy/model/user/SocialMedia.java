package ch.usi.si.bsc.sa4.devinecodemy.model.user;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.SocialMediaDTO;

/**
 * The SocialMedia class represents all the social media
 * profiles links of a certain user.
 */
public class SocialMedia {

    private String twitter;
    private String skype;
    private String linkedin;

    /**
     * Constructs a new SocialMedia object with the given values.
     * @param twitter the link to the Twitter profile.
     * @param skype the link to the Skype profile.
     * @param linkedin the link to the LinkedIn profile.
     */
    public  SocialMedia(String twitter, String skype, String linkedin) {
        this.skype = skype;
        this.twitter = twitter;
        this.linkedin = linkedin;
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

    public SocialMediaDTO toSocialMediaDTO () {
        return new SocialMediaDTO(this);
    }
}
