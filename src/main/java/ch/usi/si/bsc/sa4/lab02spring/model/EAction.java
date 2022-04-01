package ch.usi.si.bsc.sa4.lab02spring.model;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;

/**
 * All the actions at the disposal of the player.
 */
public enum EAction {
    MOVE_FORWARD("MoveForward") {
        @Override
        public String getDescription() {
            return "Moves Robot forward";
        }
    },
    TURN_LEFT   ("TurnLeft") {
        @Override
        public String getDescription() {
            return "Turns Robot left";
        }
    },
    TURN_RIGHT  ("TurnRight") {
        @Override
        public String getDescription() {
            return "Turns Robot right";
        }
    },
    COLLECT_COIN("CollectCoin") {
        @Override
        public String getDescription() {
            return "Collects a Coin";
        }
    };

    private final String name;

    EAction(String name) {
        this.name = name;
    }

    /**
     * Create a new EAction from a command name.
     * Example: "MoveForward()" -> EAction.MOVE_FORWARD, name: "MoveForward"
     * @param command the given command
     */
    public static EAction getEActionFromCommand(String command) throws IllegalArgumentException {
        switch (command) {
            case "moveForward()":
                return EAction.MOVE_FORWARD;
            case "turnLeft()":
                return EAction.TURN_LEFT;
            case "turnRight()":
                return EAction.TURN_RIGHT;
            case "collectCoin()":
                return EAction.COLLECT_COIN;
            default:
                throw new IllegalArgumentException("Unknown command: " + command);
        }

    }

    public abstract String getDescription();
    public String getName() {
        return this.name;
    }
    public EActionDTO toEActionDTO() { return new EActionDTO(this); }
}
