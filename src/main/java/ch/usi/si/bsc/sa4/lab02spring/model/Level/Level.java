package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="levels")
public class Level {
    @Id
    private String id;
    private final String name;
    private final String description;

    // stats
//    private int time_limit;
//    private int max_steps;

    // board
    private Board board;
    private Robot robot;

    private EAction[] allowed_commands;

    @PersistenceConstructor
    public Level(String name, String description, Board board, Robot robot, EAction[] allowed_commands) {
        this.name = name;
        this.description = description;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
    }
    
    
    
    public LevelDTO toLevelDTO() { return new LevelDTO(this); }

    // TODO
    public boolean validatePath(final EAction[] commands) {
        int x = robot.getPos_x();
        int y = robot.getPos_y();

        int current_orientation;

        for(EAction command : commands) {

            if (command == EAction.MOVE_FORWARD) {
                // Do something
            }

            // ...
        }


        return true;
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
    
    public EAction[] getAllowed_commands(){
        return allowed_commands;
    }

}
