package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import org.springframework.data.util.Pair;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {

    private String name;
    private int worldNumber;
    private String descriptionMessage;
    private String congratulationsMessage;

    private int firstLevelNumber;

    private int lastLevelNumber;

    /**
     * Constructor a new empty EWorld DTO object.
     */
    public EWorldDTO () {
    }
    /**
     * Constructor for the EWorld DTO.
     * @param world the EWorld t be represented by this enum.
     * @param levelNumberRange the range of levelNumbers for the levels
     *                         in this EWorld, going from the first level
     *                         to the last level.
     */
    public EWorldDTO (EWorld world, Pair<Integer, Integer> levelNumberRange) {
        name = world.getDisplayName();
        worldNumber = world.getWorldNumber();
        descriptionMessage= world.getDescriptionMessage();
        congratulationsMessage = world.getCongratulationsMessage();
        this.firstLevelNumber = levelNumberRange.getFirst();
        this.lastLevelNumber = levelNumberRange.getSecond();
    }
    
    public String getName(){
        return name;
    }

    public int getWorldNumber(){
        return worldNumber;
    }
    
    public String getDescriptionMessage() {
        return descriptionMessage;
    }
    
    public String getCongratulationsMessage(){
        return congratulationsMessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorldNumber(int worldNumber) {
        this.worldNumber = worldNumber;
    }

    public void setDescriptionMessage(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    public void setCongratulationsMessage(String congratulationsMessage) {
        this.congratulationsMessage = congratulationsMessage;
    }

    public void setFirstLevelNumber(int firstLevelNumber) {
        this.firstLevelNumber = firstLevelNumber;
    }

    public void setLastLevelNumber(int lastLevelNumber) {
        this.lastLevelNumber = lastLevelNumber;
    }

    public int getFirstLevelNumber() { return firstLevelNumber; }

    public int getLastLevelNumber() { return lastLevelNumber;}
}
