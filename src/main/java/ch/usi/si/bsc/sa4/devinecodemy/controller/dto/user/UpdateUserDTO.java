package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;


/**
 * The UpdateUserDTO class represents the new user state of
 * the user to be updated.
 * (needed to check if the field publicProfile is initialized or not).
 */
public class UpdateUserDTO {
    private String id;
    private boolean publicProfileInitialized;
    private boolean publicProfile;

    /**
     * Constructs a new UpdateUserDTO object
     * with unsetted values.
     */
    public UpdateUserDTO() {
    }

    /**
     * Constructs a new UpdateUserDTO with the field to be modified
     * of the user with the given id.
     * @param id                       the id of the user to be updated.
     * @param publicProfileInitialized whether the publicProfile was initialized.
     * @param publicProfile            new value of the publicProfile field telling
     *                                 whether the profile should be public or not.
     */
    public UpdateUserDTO(String id, boolean publicProfileInitialized, boolean publicProfile) {
        this.id = id;
        this.publicProfileInitialized = publicProfileInitialized;
        this.publicProfile = publicProfile;
    }

    public String getId() {
        return id;
    }

    public boolean isPublicProfileInitialized() {
        return publicProfileInitialized;
    }

    public boolean isPublicProfile() {
        return publicProfile;
    }
}
