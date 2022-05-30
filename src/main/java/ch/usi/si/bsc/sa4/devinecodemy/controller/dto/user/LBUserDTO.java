package ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user;

import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Contains the data of a user to be displayd in the leaderboard.
 */
public class LBUserDTO extends GeneralUserDTO{

    private final int completedLevels;
    private final String avatarUrl;

    /**
     * Constructs a new LBUserDTO object of the given user
     * with the given number of completed levels and avatarUrl.
     * @param user the user to be mapped in the dto.
     * @param completedLevels the number of completed levels
     *                       of the dto.
     * @param avatarUrl the avatarUrl of the dto.
     */
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
