package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The PlayLevelDTO class represents the input commands by a user
 * while playing a specific level.
 */
public class PlayLevelDTO {

    private final int levelNumber;

    private final List<String> commands;

    /**
     * Constructs a PlayLevelDTO object to play the level corresponding
     * to the given levelNumber with the given commands.
     * @param levelNumber the levelNumber of the level to be played.
     * @param commands the commands to be played.
     */
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
