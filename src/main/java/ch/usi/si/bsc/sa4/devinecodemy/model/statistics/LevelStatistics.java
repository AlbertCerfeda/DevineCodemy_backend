package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;

import java.util.ArrayList;
import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;

/**
 * Represents all the statistics data for a specific level.
 */
public class LevelStatistics {

    private boolean completed;

    private List<String> data;


    public LevelStatistics (boolean completed) {
        this.completed = completed;
        this.data = new ArrayList<>();
    }

    public List<String> getAllAttempts() {
        return data;
    }

    public void setData(List<String> data) { this.data = data; }

    /**
     * Adds the list of commands used in a game played.
     *
     * @param attempt the string representing the blockly commands used in the game.
     */
    public void add(String attempt){
        data.add(attempt);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    /**
     * Returns a specific attempt for the level.
     *  If attempt number is -1, returns the last attempt.
     * @param attemptNumber the attempt number to return.
     *                      if -1 returns the last attempt
     * @return the specific attempt for the level.
     * @throws StatisticInexistentException if the statistic does not exist.
     */
    public String getAttempt(int attemptNumber) throws StatisticInexistentException {
        try {
            return data.get(attemptNumber == -1 ? data.size()-1 : attemptNumber);
        }catch (IndexOutOfBoundsException e){
            throw new StatisticInexistentException();
        }

    }

}
