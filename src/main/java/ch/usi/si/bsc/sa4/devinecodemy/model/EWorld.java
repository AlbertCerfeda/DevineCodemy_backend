package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    EARTH("EARTH", "Sample description for the EARTH world !","Congrats!", 1, 5), //Example descriptions
    SKY("SKY", "Sample description for the SKY world !","Congrats!",6, 10),
    
    LAVA("LAVA", "Sample description for the LAVA world !","Congrats!", 11,15);

    
    private final String name;
    private final String descriptionMessage;
    private final String congratulationsMessage;

    private int firstLevelNumber;

    private int lastLevelNumber;
    
    EWorld(String name, String descriptionMessage, String congratulationsMessage, int firstLevelNumber, int lastLevelNumber){
        this.name = name;
        this.descriptionMessage = descriptionMessage;
        this.congratulationsMessage = congratulationsMessage;
        this.firstLevelNumber = firstLevelNumber;
        this.lastLevelNumber = lastLevelNumber;
    }
    
    /**
     * Create a new EWorld from a world name.
     * Example: "LAVA" -> EWorld.LAVA
     * @param world the given world
     * @throws IllegalArgumentException if the string does not correspond to a valid EWorld value.
     */
    public static EWorld getEWorldFromString(String world) throws IllegalArgumentException {
        for (EWorld eworld : EWorld.values()) {
            if (eworld.getName().equals(world)) {
                return eworld;
            }
        }
        
        
        throw new IllegalArgumentException("Unknown world: '" + world + "'");
    }
    
    public String getDescriptionMessage() {
        return descriptionMessage;
    }
    public String getCongratulationsMessage() {
        return congratulationsMessage;
    }

    public int getFirstLevelNumber(){
        return firstLevelNumber;
    }

    public int getLastLevelNumber() {
        return lastLevelNumber;
    }

    public String getName(){
        return name;
    }
    
    public EWorldDTO toEWorldDTO() { return new EWorldDTO(this); }

}
