package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;


import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;


public class LevelDTO {
    private final String name;
    private final String description;
    
    private BoardDTO board;
    private RobotDTO robot;
    
    private final List<EActionDTO> allowed_commands;
    
    private final int maxCommandsNumber;

    private int levelNumber;
    
    /**
     * Constructor for the LevelDTO object.
     * @param level the Level object from which to retrieve the DTO data.
     * @param onlyinfo whether to store only the Level info.
     */
    public LevelDTO(Level level, boolean onlyinfo) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.board = level.getBoard().toBoardDTO();
        
        this.robot = level.getRobot().toRobotDTO();
        
        this.maxCommandsNumber = level.getMaxCommandsNumber();

        this.levelNumber = level.getLevelNumber();
        
        allowed_commands = level.getAllowed_commands().stream().map(EAction::toEActionDTO).collect(Collectors.toList());
        
        if(onlyinfo) {
            this.board = null;
            this.robot = null;
            
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public RobotDTO getRobot() {
        return robot;
    }

    public List<EActionDTO> getAllowed_commands() {
        return allowed_commands;
    }

    public int getMaxCommandsNumber() {
        return maxCommandsNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
