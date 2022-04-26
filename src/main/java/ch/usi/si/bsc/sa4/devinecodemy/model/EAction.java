package ch.usi.si.bsc.sa4.lab02spring.model;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;

/**
 * All the actions at the disposal of the player.
 */
public enum EAction {
    MOVE_FORWARD("moveForward", "Moves Robot forward"),
    TURN_LEFT   ("turnLeft",    "Turns Robot left"),
    TURN_RIGHT  ("turnRight",   "Turns Robot right"),
    COLLECT_COIN("collectCoin", "Collects a Coin");
    
    // The function call for this command (eg 'moveForward' )
    private final String func_call;
    // The description for the command
    private final String description;
    
    EAction(String func_call, String description) {
        this.func_call= func_call;
        this.description = description;
    }

    /**
     * Create a new EAction from a command name.
     * Example: "moveForward" -> EAction.MOVE_FORWARD, name: "moveForward"
     * @param command the given command
     * @throws IllegalArgumentException if the command does not correspond to a valid function call.
     */
    public static EAction getEActionFromCommand(String command) throws IllegalArgumentException {
        for (EAction action : EAction.values())
            
            if(action.getFunc_call() == command)
                return action;
    
        throw new IllegalArgumentException("Unknown command: '" + command + "'");
    }
    
    public String getFunc_call()  { return func_call; }
    public String getDescription(){ return description; }
    
    public EActionDTO toEActionDTO() { return new EActionDTO(this); }
}
