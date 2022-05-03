package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

public class ChangePasswordDTO {
    private String userId;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordDTO() {}

    public ChangePasswordDTO(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
