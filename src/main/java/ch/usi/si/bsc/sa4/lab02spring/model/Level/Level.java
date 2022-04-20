package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="levels")
public class Level {
    @Id
    private String id;
    private final String name;
    private final String description;
    
    private final int maxCommandsNumber;
    
    /*
    TODO: Add additional fields
    - Goal of the level in order to complete it
    - Thumbnail image of the level
     */
    
    private final Board board;
    private final Robot robot;

    private final List<EAction> allowed_commands;

    @PersistenceConstructor
    public Level(String name, String description, Board board, Robot robot, List<EAction> allowed_commands, int maxCommandsNumber) {
        this.name = name;
        this.description = description;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
        this.maxCommandsNumber = maxCommandsNumber;
    }
    
    public LevelDTO toLevelDTO() { return new LevelDTO(this); }
    
    
    // Getters and setters
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }

    public Board getBoard() {
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

}
