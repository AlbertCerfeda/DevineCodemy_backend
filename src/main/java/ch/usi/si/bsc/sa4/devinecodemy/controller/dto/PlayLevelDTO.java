package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;

/**
 * DTO representing the input commands by a user while playing a specific level
 */
public class PlayLevelDTO {

    private int levelNumber;

    private String userId;

    private List<String> commands;

    public PlayLevelDTO (int levelNumber, String userId, List<String> commands) {
        this.levelNumber = levelNumber;
        this.userId = userId;
        this.commands = commands;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getCommands() {
        return commands;
    }
}
