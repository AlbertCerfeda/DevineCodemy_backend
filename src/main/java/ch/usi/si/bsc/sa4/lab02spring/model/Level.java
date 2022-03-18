package ch.usi.si.bsc.sa4.lab02spring.model;

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

    private CommandTypes[] allowed_commands;

    @PersistenceConstructor
    public Level(String name, String description, Board board, Robot robot, CommandTypes[] allowed_commands) {
        this.name = name;
        this.description = description;
        this.board = board;
        this.robot = robot;
        this.allowed_commands = allowed_commands;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public boolean validatePath(final CommandTypes[] commands) {
        int x = robot.getStart_x();
        int y = robot.getStart_y();

        int current_orientation;

        for(CommandTypes command : commands) {

            if (command == CommandTypes.MOVE_FORWARD) {
                // Do something
            }

            // ...
        }


        return true;
    }



}
