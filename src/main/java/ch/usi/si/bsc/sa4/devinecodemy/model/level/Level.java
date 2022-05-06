package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;

import java.util.List;
import java.util.Objects;

/**
 * The Level class represents the Level to be played.
 */
@Document(collection="levels")
public class Level {
    @Id
    private String id;
    private final String name;
    private final String description;

    @Indexed(unique = true)
    private final int levelNumber;
    
    private final int maxCommandsNumber;

    private final String thumbnailSrc;
    /*
    TODO: Add additional fields
    - Goal of the level in order to complete it
    - Thumbnail image of the level
     */
    
    private final Board board;
    private final Robot robot;

    /**
     * A collection of commands that is possible
     * to play in this Level.
     */
    private final List<EAction> allowedCommands;

    @PersistenceConstructor
    @JsonCreator
    public Level(@JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("levelNumber") int levelNumber,
                 @JsonProperty("maxCommandsNumber") int maxCommandsNumber,
                 @JsonProperty("board") Board board,
                 @JsonProperty("robot") Robot robot,
                 @JsonProperty("allowed_commands") List<EAction> allowedCommands,
                 @JsonProperty("thumbnailSrc") String thumbnailSrc) {
        this.name = name;
        this.description = description;
        this.levelNumber = levelNumber;
        this.maxCommandsNumber = maxCommandsNumber;
        this.board = board;
        this.robot = robot;
        this.allowedCommands = allowedCommands;
        this.thumbnailSrc=thumbnailSrc;
    }
    
    /**
     * Returns a LevelDTO containing ALL the data.
     * @return the LevelDTO.
     */
    public LevelDTO toLevelDTO() { return new LevelDTO(this, false); }
    
    /**
     * Returns a LevelDTO containing only the Level info.
     * @return the LevelDTO.
     */
    public LevelDTO toLevelInfoDTO() { return new LevelDTO(this, true); }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Level)) {
            return false;
        }
        final Level level = (Level) o;
        return this.id.equals(level.id) && this.name.equals(level.name) && this.description.equals(level.description) && this.levelNumber == level.levelNumber &&
                this.allowedCommands.equals(level.allowedCommands) && level.board.equals(this.board) && level.robot.equals(this.robot) && this.maxCommandsNumber == level.maxCommandsNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,name,description,levelNumber,maxCommandsNumber,thumbnailSrc,board,robot, allowedCommands);
    }

    // Getters and setters
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public Board getBoard(){
        return board;
    }
    
    public Robot getRobot(){
        return robot;
    }
    
    public int getMaxCommandsNumber(){
        return maxCommandsNumber;
    }
    
    public List<EAction> getAllowedCommands(){
        return allowedCommands;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailSrc() {return thumbnailSrc; }

    public int getLevelNumber() {return levelNumber;}
}
