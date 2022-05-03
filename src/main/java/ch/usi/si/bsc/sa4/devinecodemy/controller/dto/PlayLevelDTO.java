package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DTO representing the input commands by a user while playing a specific level
 */
public class PlayLevelDTO {

    private final int levelNumber;

    private final List<String> commands;

    public PlayLevelDTO (@JsonProperty("levelNumber") int levelNumber,
                         @JsonProperty("commands") List<String> commands) {
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
