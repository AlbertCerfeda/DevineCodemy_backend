package ch.usi.si.bsc.sa4.lab02spring.model.Level;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Random;

@Document(collection="levels")
public class Level {
    @Id
    private String id;
    private final String name;
    private final String description;
    
    private final int max_steps;
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
                 @JsonProperty("max_steps") int max_steps,
                 @JsonProperty("board") Board board,
                 @JsonProperty("robot") Robot robot,
                 @JsonProperty("allowed_commands") List<EAction> allowed_commands) {
        this.name = name;
        this.description = description;
        this.max_steps = max_steps;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
    }

    public Level(final String name, final String description, int max_steps, final int dim_x, final int dim_y, List<EAction> allowed_commands) {
        Random rand = new Random();
        final int start_x = rand.nextInt(dim_x);
        final int start_y = rand.nextInt(dim_y);

        this.name = name;
        this.description = description;
        this.max_steps = max_steps;
        this.board = new Board(dim_x, dim_y, start_x, start_y);
        this.robot = new Robot(start_x, start_y, EOrientation.getRandom());
        this.allowed_commands = allowed_commands;
    }
    
    
    
    public boolean validateActions(final List<EAction> commands) {
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
    
    public List<EAction> getAllowed_commands(){
        return allowed_commands;
    }

}
