package ch.usi.si.bsc.sa4.devinecodemy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;

/**
 * Parses a sequence of String commands into EActions and plays them on a level.
 */
public class GamePlayer {

    private final Level level;
    private final List<EAction> parsedCommands = new ArrayList<>();

    /**
     * Constructor for GamePlayer.
     * @param level the level to play.
     */
    public GamePlayer(final Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public List<EAction> getParsedCommands() {
        return parsedCommands;
    }

    /**
     * Validates a sequence of commands on the level.
     * - Parses the Strings into their corresponding EAction ENUMs and checks for errors.
     * - Plays (if no errors occurred) the sequence of EActions on the game level.
     * @param commands the commands to parse and execute.
     * @return a LevelValidation object to represent the result of the validation.
     */
    public LevelValidation play(final List<String> commands) {

        LevelValidation levelValidation = new LevelValidation();

        ////
        //  Parsing phase
        //      Checks for invalid commands
        //      Produces, if correct, List<EAction>
        List<EAction> actions = parseCommands(commands, levelValidation);
        if (levelValidation.getAnimations().contains(EAnimation.EMOTE_DEATH)) {
            return levelValidation;
        }

        parsedCommands.addAll(actions);

        ////
        // Game playing phase

        return playActions(actions, levelValidation);
    }

    /**
     * Parses a list of String commands into an equivalent list of EAction only if valid.
     *  - If commands aren't existing, the levelValidation is set with errors
     * @param commands the commands to be parsed.
     * @param levelValidation the levelValidation object.
     * @return the parsed list of EActions to be run.
     */
    private List<EAction> parseCommands(List<String> commands, LevelValidation levelValidation) {

        List<EAction> actions = new ArrayList<>();
        boolean hasErrors = false;

        for (String command : commands) {
            try {
                EAction action = EAction.getEActionFromCommand(command.trim());
                // If the command is not allowed
                if(!level.getAllowed_commands().contains(action)) {
                    hasErrors = true;
                    levelValidation.addError("Command not allowed: '"+action.getFuncCall()+"'");
                } else
                    actions.add(action);
            } catch (IllegalArgumentException e) {
                hasErrors = true;
                levelValidation.addError(e.getMessage());
            }
        }

        if (actions.size() > level.getMaxCommandsNumber()) {
            hasErrors = true;
            levelValidation.addError("Too many commands: you can use only " + level.getMaxCommandsNumber() + " commands.");
        }

        if (hasErrors) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
        }

        return actions;
    }

    /**
     * Evaluates a list of actions, setting completed only once all the Items were collected.
     * @param actions the list of EActions to be played.
     * @param levelValidation the levelValidation.
     * @return the levelValidation object.
     */
    private LevelValidation playActions(List<EAction> actions, LevelValidation levelValidation) {
        boolean isDead = false;

        Robot robot = level.getRobot();
        Board board = level.getBoard();
        int currentX = robot.getPosX();
        int currentY = robot.getPosY();
        EOrientation currentOrientation = robot.getOrientation();

        int collectedItems = 0;

        for (EAction action : actions) {
            if(isDead)
                break;

            switch (action) {
                case MOVE_FORWARD:
                    if (board.canStep(currentX, currentY, currentOrientation)) {
                        currentX += currentOrientation.getDeltaX();      // update x position
                        currentY += currentOrientation.getDeltaY();      // update y position
                        levelValidation.addAnimation(EAnimation.MOVE_FORWARD);
                    } else { // Wrong command, cannot step (out of the board or too high to step on) : animate death and exit
                        levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
                        isDead = true; // stop playing the game, from now on we will only check for errors
                    }
                    break;
                case TURN_LEFT:
                    currentOrientation = currentOrientation.turnLeft();   // update orientation
                    levelValidation.addAnimation(EAnimation.TURN_LEFT);
                    break;
                case TURN_RIGHT:
                    currentOrientation = currentOrientation.turnRight();  // update orientation
                    levelValidation.addAnimation(EAnimation.TURN_RIGHT);
                    break;
                case COLLECT_COIN:
                    levelValidation.addAnimation(EAnimation.JUMP);
                    if (board.containsItemAt(currentX, currentY)) {
                        ++collectedItems;
                    } else {
                        levelValidation.addAnimation(EAnimation.EMOTE_NO);
                    }
                    break;
                default:
                    Logger logger = Logger.getLogger(this.getClass().getName());
                    logger.severe("[X] '" + action.name() +"' action unhandled !!");
                    break;
            }
        }

        if (collectedItems == board.getCoinsNumber()) {     // level completed
            levelValidation.addAnimation(EAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        } else if (!isDead) { // level not completed but the player is not dead
            levelValidation.setCompleted(false);
        } // level not completed and the player is dead

        return levelValidation;
    }
}
