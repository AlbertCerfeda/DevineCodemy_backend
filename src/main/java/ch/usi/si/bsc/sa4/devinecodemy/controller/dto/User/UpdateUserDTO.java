package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.User;


/**
 * DTO for updating the user
 * (needed to check if the field publicProfile is initialized or not)
 */
public class UpdateUserDTO {
    private String id;
    private boolean publicProfileInitialized;
    private boolean publicProfile;

    public UpdateUserDTO() {}

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
