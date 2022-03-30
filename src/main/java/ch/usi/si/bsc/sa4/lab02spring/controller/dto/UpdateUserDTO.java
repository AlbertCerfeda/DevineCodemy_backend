package ch.usi.si.bsc.sa4.lab02spring.controller.dto;


/**
 * DTO for updating the user
 * (needed to check if the field publicProfile is initialized or not)
 */
public class UpdateUserDTO {
    private String id;
    private boolean modifyProfile;
    private boolean publicProfile;

    public UpdateUserDTO() {}

    public UpdateUserDTO(String id, boolean modifyProfile, boolean publicProfile) {
        this.id = id;
        this.modifyProfile = modifyProfile;
        this.publicProfile = publicProfile;
    }

    public String getId() {
        return id;
    }

    public boolean shouldModifyProfile() {
        return modifyProfile;
    }

    public boolean isPublicProfile() {
        return publicProfile;
    }
}
