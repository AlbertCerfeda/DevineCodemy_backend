package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private final Board board;
    private final Robot robot;
    private final LevelValidation levelValidation;
    private final Map<String, Action> functionTable = new HashMap<>();

    private int collectedCoins = 0;

    private boolean isDead = false;

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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getCollectedCoins() {
        return collectedCoins;
    }

    public void incrementCollectedCoins() {
        collectedCoins++;
    }

    public void setFunctionTable(final Map<String, Action> functionTable) {
        this.functionTable.clear();
        this.functionTable.putAll(functionTable);
    }

}
