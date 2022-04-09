package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.EAnimation;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Board;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Robot;
import ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation.LevelValidation;


public class GamePlayer {

    private final Level level;
    private final String[] commands;

    /**
     * Constructor for GamePlayer
     * @param level the level to play.
     * @param commands the commands to execute.
     */
    public GamePlayer(final Level level, final String[] commands) {
        this.level = level;
        this.commands = commands;
    }

    /**
     * To validate a gameplay on a level.
     * @return a LevelValidation object to represent the result of the validation.
     */
    public LevelValidation play() {
        boolean hasErrors = false;
        boolean isDead = false;

        LevelValidation levelValidation = new LevelValidation();

        Robot robot = level.getRobot();
        Board board = level.getBoard();
        int current_x = robot.getPos_x();
        int current_y = robot.getPos_y();
        EOrientation current_orientation = robot.getOrientation();

        int collectedItems = 0;
        int commandsCount = 0; // TODO: implement check for command limit.


        for (String command : commands) {
            try {
                EAction action = EAction.getEActionFromCommand(command.trim());
                // The command is valid: but if the code has errors or the animation cannot continue, we cannot continue the game
                if (!isDead && !hasErrors) {
                    switch (action) {
                        case MOVE_FORWARD:
                            if (board.canStep(current_x, current_y, current_orientation)) {
                                current_x += current_orientation.getDelta_x();      // update x position
                                current_y += current_orientation.getDelta_y();      // update y position
                                levelValidation.addAnimation(EAnimation.MOVE_FORWARD);
                            } else { // Wrong command, cannot step (out of the board or too high to step on) : animate death and exit
                                levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
                                isDead = true; // stop playing the game, from now on we will only check for errors
                            }
                            break;
                        case TURN_LEFT:
                            current_orientation = current_orientation.turnLeft();   // update orientation
                            levelValidation.addAnimation(EAnimation.TURN_LEFT);
                            break;
                        case TURN_RIGHT:
                            current_orientation = current_orientation.turnRight();  // update orientation
                            levelValidation.addAnimation(EAnimation.TURN_RIGHT);
                            break;
                        case COLLECT_COIN:
                            levelValidation.addAnimation(EAnimation.JUMP);
                            if (board.containsItemAt(current_x, current_y)) {
                                ++collectedItems;
                            } else {
                                levelValidation.addAnimation(EAnimation.EMOTE_NO);
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                levelValidation.addError("Unknown command: " + command);
                hasErrors = true;
            }
        }

        if (hasErrors) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
        } else if (collectedItems == board.getCoinsNumber()) {     // level completed
            levelValidation.addAnimation(EAnimation.EMOTE_THUMBS_UP);
            levelValidation.addAnimation(EAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        } else if (!isDead) { // level not completed but the player is not dead
            levelValidation.addAnimation(EAnimation.EMOTE_NO);
            levelValidation.addAnimation(EAnimation.IDLE);
            levelValidation.setCompleted(false);
        } // level not completed and the player is dead
        
        return levelValidation;
    }
}
