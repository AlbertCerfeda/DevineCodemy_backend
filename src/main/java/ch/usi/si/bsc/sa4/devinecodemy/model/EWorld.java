package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    EARTH("Part of the earth levels","Congrats!"), //Example descriptions
    SKY("Part of the sky levels","Congrats!"),
    
    LAVA("Part of the lava levels","Congrats!");

    //Description of the world
    private final String descriptionMessage;
    private final String congratulationsMessage;
    
    EWorld(String descriptionMessage, String congratulationsMessage){
        this.descriptionMessage = descriptionMessage;
        this.congratulationsMessage = congratulationsMessage;
    }
    
    public String getDescriptionMessage() {
        return descriptionMessage;
    }
    public String getCongratulationsMessage() {
        return congratulationsMessage;
    }
    
    public EWorldDTO toEWorldDTO() { return new EWorldDTO(this); }

}
