package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;


import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The LevelDTO class represents the Level to be played
 * by a player.
 */
public class LevelDTO {
    private final String name;
    private final String description;
    private final String levelWorld;


    private BoardDTO board;
    private RobotDTO robot;

    private final List<ECategoryDTO> allowedCommands;

    private final String thumbnailSrc;

    private final int maxCommandsNumber;

    private final int levelNumber;

    /**
     * Constructs a new LevelDTO object with the given values.
     * @param name the name of the level.
     * @param description the description of the level.
     * @param levelNumber the number of the level.
     * @param levelWorld the world of the level.
     * @param maxCommandsNumber the max commands number of the level.
     * @param board the board of the level.
     * @param robot the robot of the level.
     * @param allowedCommands the allowed Commands of the level.
     * @param thumbnailSrc the thumbnail source of the level.
     */
    @JsonCreator
    public LevelDTO(@JsonProperty("name") String name,
                    @JsonProperty("description") String description,
                    @JsonProperty("levelNumber") int levelNumber,
                    @JsonProperty("levelWorld") String levelWorld,
                    @JsonProperty("maxCommandsNumber") int maxCommandsNumber,
                    @JsonProperty("board") BoardDTO board,
                    @JsonProperty("robot") RobotDTO robot,
                    @JsonProperty("allowedCommands") List<ECategoryDTO> allowedCommands,
                    @JsonProperty("thumbnailSrc") String thumbnailSrc) {
        this.name = name;
        this.description = description;
        this.levelNumber = levelNumber;
        this.levelWorld = levelWorld;
        this.maxCommandsNumber = maxCommandsNumber;
        this.board = board;
        this.robot = robot;
        this.allowedCommands = allowedCommands;
        this.thumbnailSrc = thumbnailSrc;
    }

    /**
     * Constructs a LevelDTO object matching the given Level.
     * @param level the Level object from which to retrieve the DTO data.
     * @param onlyInfo whether to store only the Level info.
     */
    public LevelDTO(Level level, boolean onlyInfo) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.levelWorld = level.getLevelWorld().getDisplayName();

        this.board = level.getBoard().toBoardDTO();

        this.robot = level.getRobot().toRobotDTO();

        this.maxCommandsNumber = level.getMaxCommandsNumber();

        this.levelNumber = level.getLevelNumber();

        allowedCommands = level.getAllowedCommands().stream().map(ECategory::toEActionDTO).collect(Collectors.toList());

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
    
    public String getLevelWorld(){
        return levelWorld;
    }

    public List<ECategoryDTO> getAllowedCommands() {
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
