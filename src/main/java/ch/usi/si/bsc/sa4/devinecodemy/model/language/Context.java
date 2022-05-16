package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;

import java.util.HashMap;
import java.util.Map;

/**
 * The context of the game. It contains:
 *  - the board on which the game is played
 *  - the robot that is controlled by the player's program,
 *  - the level validation,
 *  - the function table that maps the names of the functions to their implementations,
 *  - the number of collected coins,
 *  - a boolean representing if the player is dead or not.
 */
public class Context {

    private final Board board;
    private final Robot robot;
    private final LevelValidation levelValidation;
    private final Map<String, Action> functionTable = new HashMap<>();

    private int collectedCoins = 0;

    private boolean isDead = false;

    /**
     * Constructor for the context.
     * @param board the board on which the game is played.
     * @param robot the robot controlled by the player's program.
     * @param levelValidation the result of the game validation.
     */
    public Context(final Board board, final Robot robot, final LevelValidation levelValidation) {
        this.board = board;
        this.robot = robot;
        this.levelValidation = levelValidation;
    }

    public Board getBoard() {
        return board;
    }

    public Robot getRobot() {
        return robot;
    }

    public LevelValidation getLevelValidation() {
        return levelValidation;
    }

    public Map<String, Action> getFunctionTable() {
        return functionTable;
    }

    /**
     * Returns whether the player is dead or not.
     * @return true if the player is dead, false otherwise.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets the player's death status.
     * @param isDead true if the player is dead, false otherwise.
     */
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    /**
     * Increments the number of collected coins.
     */
    public void incrementCollectedCoins() {
        collectedCoins++;
    }

    /**
     * To set the function table of this program.
     * @param functionTable the function table of this program.
     */
    public void setFunctionTable(final Map<String, Action> functionTable) {
        this.functionTable.clear();
        this.functionTable.putAll(functionTable);
    }

}
