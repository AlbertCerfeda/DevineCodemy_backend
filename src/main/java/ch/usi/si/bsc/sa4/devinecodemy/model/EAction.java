package ch.usi.si.bsc.sa4.devinecodemy.model;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EActionDTO;

/**
 * All the actions at the disposal of the player.
 */
public enum EAction {
    MOVE_FORWARD("moveForward", "Moves Robot forward"),
    TURN_LEFT("turnLeft", "Turns Robot left"),
    TURN_RIGHT("turnRight", "Turns Robot right"),
    COLLECT_COIN("collectCoin", "Collects a Coin");

    // The function call for this command (eg 'moveForward' )
    private final String funcCall;
    // The description for the command
    private final String description;

    /**
     * Creates an EAction with the given funcCall and description.
     * @param funcCall the name of the func to be called when the action is executed.
     * @param description the description of the action.
     */
    EAction(String funcCall, String description) {
        this.funcCall = funcCall;
        this.description = description;
    }

    /**
     * Create a new EAction from a command name.
     * Example: "moveForward" -> EAction.MOVE_FORWARD, name: "moveForward"
     *
     * @param command the given command
     * @throws IllegalArgumentException if the command does not correspond
     *                                  to a valid function call.
     */
    public static EAction getEActionFromCommand(String command) throws IllegalArgumentException {
        for (final EAction action : EAction.values()) {
            if (action.getFuncCall().equals(command)) {
                return action;
            }
        }


        throw new IllegalArgumentException("Unknown command: '" + command + "'");
    }

    public String getFuncCall() {
        return funcCall;
    }

    public String getDescription() {
        return description;
    }

    public EActionDTO toEActionDTO() {
        return new EActionDTO(this);
    }
}
