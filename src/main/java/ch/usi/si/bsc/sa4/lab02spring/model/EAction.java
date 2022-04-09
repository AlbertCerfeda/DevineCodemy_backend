package ch.usi.si.bsc.sa4.lab02spring.model;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;

/**
 * All the actions at the disposal of the player.
 */
public enum EAction {
    MOVE_FORWARD("moveForward") {
        @Override
        public String getDescription() {
            return "Moves Robot forward";
        }
    },
    TURN_LEFT   ("turnLeft") {
        @Override
        public String getDescription() {
            return "Turns Robot left";
        }
    },
    TURN_RIGHT  ("turnRight") {
        @Override
        public String getDescription() {
            return "Turns Robot right";
        }
    },
    COLLECT_COIN("collectCoin") {
        @Override
        public String getDescription() {
            return "Collects a Coin";
        }
    };
    
    // The function call for this command (eg 'moveForward' )
    private final String func_call;

    EAction(String func_call) {
        this.func_call= func_call;
    }

    /**
     * Create a new EAction from a command name.
     * Example: "moveForward" -> EAction.MOVE_FORWARD, name: "moveForward"
     * @param command the given command
     * @throws IllegalArgumentException if the command does not correspond to a valid function call.
     */
    public static EAction getEActionFromCommand(String command) throws IllegalArgumentException {
        for (EAction action : EAction.values())
            if(action.name() == command)
                return action;
    
        throw new IllegalArgumentException("Unknown command: '" + command + "'");
    }

    public abstract String getDescription();
    
    public EActionDTO toEActionDTO() { return new EActionDTO(this); }
}
