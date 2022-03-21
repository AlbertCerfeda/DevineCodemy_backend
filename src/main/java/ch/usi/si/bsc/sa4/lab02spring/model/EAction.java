package ch.usi.si.bsc.sa4.lab02spring.model;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;

/**
 * All the actions at the disposal of the player.
 */
public enum EAction {
    MOVE_FORWARD("Moves Robot forward"),
    TURN_LEFT   ("Turns Robot left"),
    TURN_RIGHT  ("Turns Robot right"),
    COLLECT_COIN("Collects a Coin");
    
    
    private String description;
    EAction(String description) {
        this.description = description;
    }
    
    public String getDescription() { return description;}
    public EActionDTO toEActionDTO() { return new EActionDTO(this); }
}
