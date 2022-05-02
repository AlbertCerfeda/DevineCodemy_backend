package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;

/**
 * DTO representing the input commands by a user while playing a specific level
 */
public class PlayLevelDTO {

    private int levelNumber;

    private List<String> commands;

    public PlayLevelDTO (int levelNumber, List<String> commands) {
        this.levelNumber = levelNumber;
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
