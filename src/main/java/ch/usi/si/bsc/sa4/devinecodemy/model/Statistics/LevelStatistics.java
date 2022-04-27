package ch.usi.si.bsc.sa4.devinecodemy.model.Statistics;

import java.util.ArrayList;
import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Represents all the statistics data for a specific level.
 */
public class LevelStatistics {

    private boolean completed;

    private List<List<EAction>> data;

    public List<List<EAction>> getAll_attempts() {
        return data;
    }

    public LevelStatistics () {
        this.completed = false;
        this.data = new ArrayList<>();
    }

    /**
     * Adds the list of commands used in a game played.
     *
     * @param game the game from which to retrieve the statistics.
     */
    public void add(GamePlayer game){
        data.add(game.getParsed_commands());
    }
}
