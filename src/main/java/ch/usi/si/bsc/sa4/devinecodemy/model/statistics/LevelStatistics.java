package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;

import java.util.ArrayList;
import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;

/**
 * Represents all the statistics data for a specific level.
 */
public class LevelStatistics {

    private boolean completed;

    private List<List<EAction>> data;


    public LevelStatistics (boolean completed) {
        this.completed = completed;
        this.data = new ArrayList<>();
    }

    public List<List<EAction>> getAllAttempts() {
        return data;
    }

    public void setData(List<List<EAction>> data) { this.data = data; }

    /**
     * Adds the list of commands used in a game played.
     *
     * @param game the game from which to retrieve the statistics.
     */
    public void add(GamePlayer game){
        data.add(game.getParsedCommands());
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<EAction> getLastAttempt() {
        return data.get(data.size() - 1);
    }

    public List<EAction> getAttempt(int attemptNumber) throws StatisticInexistentException {
        try {
            return data.get(attemptNumber);
        }catch (Exception e){
            throw new StatisticInexistentException();
        }

    }

}
