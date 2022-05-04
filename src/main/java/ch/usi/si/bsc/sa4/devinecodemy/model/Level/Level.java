package ch.usi.si.bsc.sa4.devinecodemy.model.Level;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;

import java.util.List;

@Document(collection="levels")
public class Level {
    @Id
    private final String id;
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

    private final List<EAction> allowed_commands;

    @PersistenceConstructor
    @JsonCreator
    public Level(@JsonProperty("id") String id,
                 @JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("levelNumber") int levelNumber,
                 @JsonProperty("maxCommandsNumber") int maxCommandsNumber,
                 @JsonProperty("board") Board board,
                 @JsonProperty("robot") Robot robot,
                 @JsonProperty("allowed_commands") List<EAction> allowed_commands,
                 @JsonProperty("thumbnailSrc") String thumbnailSrc) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.levelNumber = levelNumber;
        this.maxCommandsNumber = maxCommandsNumber;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
        this.thumbnailSrc = thumbnailSrc;
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
    
    public List<EAction> getAllowed_commands(){
        return allowed_commands;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailSrc() {return thumbnailSrc; }

    public int getLevelNumber() {return levelNumber;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;
        Level level = (Level) o;
        return levelNumber == level.levelNumber && maxCommandsNumber == level.maxCommandsNumber && Objects.equals(id, level.id) && Objects.equals(name, level.name) && Objects.equals(description, level.description) && Objects.equals(thumbnailSrc, level.thumbnailSrc) && Objects.equals(board, level.board) && Objects.equals(robot, level.robot) && Objects.equals(allowed_commands, level.allowed_commands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, levelNumber, maxCommandsNumber, thumbnailSrc, board, robot, allowed_commands);
    }
}
