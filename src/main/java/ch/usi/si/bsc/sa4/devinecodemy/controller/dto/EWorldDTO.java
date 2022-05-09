package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import org.springframework.data.util.Pair;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {
    
    private final String name;
    private final String descriptionMessage;
    private final String congratulationsMessage;

    private final int firstLevelNumber;

    private final int lastLevelNumber;
    
    /**
     * Constructor for the EWorld DTO.
     * @param world the EWorld t be represented by this enum.
     * @param levelNumberRange the range of levelNumbers for the levels
     *                         in this EWorld, going from the first level
     *                         to the last level.
     */
    public EWorldDTO (EWorld world, Pair<Integer, Integer> levelNumberRange) {
        name = world.getDisplayName();
        descriptionMessage= world.getDescriptionMessage();
        congratulationsMessage = world.getCongratulationsMessage();
        this.firstLevelNumber = levelNumberRange.getFirst();
        this.lastLevelNumber = levelNumberRange.getSecond();
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescriptionMessage() {
        return descriptionMessage;
    }
    
    public String getCongratulationsMessage(){
        return congratulationsMessage;
    }

    public int getFirstLevelNumber() { return firstLevelNumber; }

    public int getLastLevelNumber() { return lastLevelNumber;}
}
