package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

/**
 * The stripped down general state of a User object.
 */
public abstract class GeneralUserDTO {

    protected String id;
    protected String name;
    protected String username;
    protected String email;
    protected String bio;

    /**
     * Constructor for any userDTO.
     * @param id id of the user.
     * @param name name of the user.
     * @param username username of the user.
     * @param email email of the user.
     * @param bio description of the user.
     */
    protected GeneralUserDTO(String id, String name, String username, String email, String bio) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
    }

    public String getId() {
        return id;
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

    public String getBio() {
        return bio;
    }
}
