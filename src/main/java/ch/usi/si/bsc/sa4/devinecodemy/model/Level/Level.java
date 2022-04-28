package ch.usi.si.bsc.sa4.devinecodemy.model.Level;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;

import java.util.List;

@Document(collection="levels")
public class Level {
    @Id
    private String id;
    private final String name;
    private final String description;
    
    private final int maxCommandsNumber;

    private final String src;
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
    public Level(@JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("maxCommandsNumber") int maxCommandsNumber,
                 @JsonProperty("board") Board board,
                 @JsonProperty("robot") Robot robot,
                 @JsonProperty("allowed_commands") List<EAction> allowed_commands,
                 @JsonProperty("src") String src) {
        this.name = name;
        this.description = description;
        this.maxCommandsNumber = maxCommandsNumber;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
        this.src = src;
    }
    
    
    public LevelDTO toLevelDTO() { return new LevelDTO(this); }
    
    
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

    public String getSrc() {return src; }
}
