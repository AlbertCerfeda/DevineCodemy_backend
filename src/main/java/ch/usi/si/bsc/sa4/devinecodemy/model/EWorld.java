package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    EARTH("EARTH", "Sample description for the EARTH world !","Congrats!"), //Example descriptions
    SKY("SKY", "Sample description for the SKY world !","Congrats!"),
    
    LAVA("LAVA", "Sample description for the LAVA world !","Congrats!");

    
    private final String name;
    private final String descriptionMessage;
    private final String congratulationsMessage;
    
    EWorld(String name, String descriptionMessage, String congratulationsMessage){
        this.name = name;
        this.descriptionMessage = descriptionMessage;
        this.congratulationsMessage = congratulationsMessage;
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
    public String getName(){
        return name;
    }
    
    public EWorldDTO toEWorldDTO() { return new EWorldDTO(this); }

}
