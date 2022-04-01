package ch.usi.si.bsc.sa4.lab02spring.service;

import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.EAnimation;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Board;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Robot;
import ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation.LevelValidation;

import java.util.Scanner;


public class GamePlayer {

    private final Level level;
    private final String commands;
    

    public GamePlayer(final Level level, final String commands) {
        this.level = level;
        this.commands = commands;
    }
    
    
    public LevelValidation play() {
        boolean hasErrors = false;
        boolean canContinueAnimation = true;

        LevelValidation levelValidation = new LevelValidation();

        Robot robot = level.getRobot();
        Board board = level.getBoard();
        int current_x = robot.getPos_x();
        int current_y = robot.getPos_y();
        EOrientation current_orientation = robot.getOrientation();

        int collectedItems = 0;
        int commandsCount = 0; // TODO: implement check for command limit.

        int lineCount = 0;

        // SCANNING FOR ERRORS and PLAYING THE GAME
        Scanner scanner = new Scanner(commands);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineCount++;

            line = line.trim(); // remove whitespaces
            line = line.split("#")[0].trim(); // remove comments
            if (line.length() > 0) { // ignore empty lines and comments
                if (!line.endsWith(";")) {
                    levelValidation.addError(lineCount, "missing semicolon at the end of the line");
                    hasErrors = true;
                }
                String[] commandsInLine = line.split(";");
                for (String command : commandsInLine) {
                    if (command.trim().length() > 0) { // ignore empty commands
                        try {
                            EAction action = EAction.getEActionFromCommand(command.trim());
                            // The command is valid: but if the code has errors or the animation cannot continue, we cannot continue the game
                            ++commandsCount;
                            if (canContinueAnimation && !hasErrors) {
                                switch (action) {
                                    case MOVE_FORWARD:
                                        if (board.canStep(current_x, current_y, current_orientation)) {
                                            current_x += current_orientation.getDelta_x();      // update x position
                                            current_y += current_orientation.getDelta_y();      // update y position
                                            levelValidation.addAnimation(lineCount, EAnimation.MOVE_FORWARD);
                                        } else { // Wrong command, cannot step (out of the board or too high to step on) : animate death and exit
                                            levelValidation.addAnimation(lineCount, EAnimation.EMOTE_DEATH);
                                            canContinueAnimation = false; // stop playing the game, from now on we will only check for errors
                                        }
                                        break;
                                    case TURN_LEFT:
                                        current_orientation = current_orientation.turnLeft();   // update orientation
                                        levelValidation.addAnimation(lineCount, EAnimation.TURN_LEFT);
                                        break;
                                    case TURN_RIGHT:
                                        current_orientation = current_orientation.turnRight();  // update orientation
                                        levelValidation.addAnimation(lineCount, EAnimation.TURN_RIGHT);
                                        break;
                                    case COLLECT_COIN:
                                        if (board.containsItemAt(current_x, current_y)) {
                                            levelValidation.addAnimation(lineCount, EAnimation.JUMP);
                                            ++collectedItems;
                                        } else {
                                            levelValidation.addAnimation(lineCount, EAnimation.EMOTE_NO);
                                        }
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            levelValidation.addError(lineCount, "unknown command: " + command);
                            hasErrors = true;
                        }
                    }
                }
            }
        }
        scanner.close();

        if (hasErrors) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(-1, EAnimation.EMOTE_DEATH);
        } else if (collectedItems == board.getCoinsNumber()) {     // level completed
            levelValidation.addAnimation(-1, EAnimation.EMOTE_THUMBS_UP);
            levelValidation.addAnimation(-1, EAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        } else { // level not completed
            levelValidation.addAnimation(-1, EAnimation.EMOTE_NO);
            levelValidation.addAnimation(-1, EAnimation.IDLE);
            levelValidation.setCompleted(false);
        }
        
        return levelValidation;
    }
}
