package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;


import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;

/**
 * The LevelDTO class represents the Level to be played
 * by a player.
 */
public class LevelDTO {
    private final String name;
    private final String description;
    
    private BoardDTO board;
    private RobotDTO robot;
    
    private final List<EActionDTO> allowedCommands;

    private final String thumbnailSrc;
    
    private final int maxCommandsNumber;

    private final int levelNumber;
    
    /**
     * Constructs a LevelDTO object matching the given Level.
     * @param level the Level object from which to retrieve the DTO data.
     * @param onlyInfo whether to store only the Level info.
     */
    public LevelDTO(Level level, boolean onlyInfo) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.board = level.getBoard().toBoardDTO();
        
        this.robot = level.getRobot().toRobotDTO();
        
        this.maxCommandsNumber = level.getMaxCommandsNumber();

        this.levelNumber = level.getLevelNumber();
        
        allowedCommands = level.getAllowedCommands().stream().map(EAction::toEActionDTO).collect(Collectors.toList());
        
        if(onlyInfo) {
            this.board = null;
            this.robot = null;
            
        }

        this.thumbnailSrc= level.getThumbnailSrc();
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

    public List<EActionDTO> getAllowedCommands() {
        return allowedCommands;
    }

    public int getMaxCommandsNumber() {
        return maxCommandsNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    
    public String getThumbnailSrc(){
        return thumbnailSrc;
    }
}
