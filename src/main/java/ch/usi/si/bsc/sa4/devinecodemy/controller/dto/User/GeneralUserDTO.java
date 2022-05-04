package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.User;

/**
 * The stripped down general state of a User object.
 */
public abstract class GeneralUserDTO {

    protected String id;
    protected String name;
    protected String username;
    protected String email;
    protected String bio;
    protected String twitter;
    protected String skype;
    protected String linkedin;

    /**
     * General constructor needed by ObjectMapper.
     */
    protected GeneralUserDTO() {
    }

    /**
     * Constructor for any userDTO.
     * @param id id of the user.
     * @param name name of the user.
     * @param username username of the user.
     * @param email email of the user.
     * @param bio description of the user.
     * @param twitter link to the Twitter profile of the user.
     * @param skype link to the Skype profile of the user.
     * @param linkedin link to the LinkedIn profile of the user.
     */
    protected GeneralUserDTO(String id, String name, String username, String email, String bio, String twitter, String skype, String linkedin) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.twitter = twitter;
        this.skype = skype;
        this.linkedin = linkedin;
    }
}
