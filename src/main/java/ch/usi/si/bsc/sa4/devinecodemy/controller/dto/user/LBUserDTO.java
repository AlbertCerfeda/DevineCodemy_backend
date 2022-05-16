package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LBUserDTO extends GeneralUserDTO{

    private final int completedLevels;
    private final String avatarUrl;

    @JsonCreator
    public LBUserDTO(User user, int completedLevels, String avatarUrl) {
        super(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getBio());
        this.completedLevels = completedLevels;
        this.avatarUrl = avatarUrl;
    }

    public int getCompletedLevels() {
        return completedLevels;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
