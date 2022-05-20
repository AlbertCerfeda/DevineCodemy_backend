package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.language.Program;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The PlayLevelDTO class represents the input commands by a user
 * while playing a specific level.
 */
public class PlayLevelDTO {

    /**
     * The levelNumber of the Level to be played.
     */
    private final int levelNumber;
    /**
     * The list of commands inserted by the user, and thus
     * represented as Strings to be parsed on execution.
     */
    private final Program program;

    private final String attempt;

    /**
     * Constructs a PlayLevelDTO object to play the level corresponding
     * to the given levelNumber with the given commands.
     * @param levelNumber the levelNumber of the level to be played.
     * @param program the program to execute.
     */
    public PlayLevelDTO (@JsonProperty("levelNumber") int levelNumber,
                         @JsonProperty("program") Program program,
                         @JsonProperty("attempt") String attempt) {
        this.levelNumber = levelNumber;
        this.program = program;
        this.attempt = attempt;
    }

    public Program getProgram() {
        return program;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getAttempt() {
        return attempt;
    }
}
