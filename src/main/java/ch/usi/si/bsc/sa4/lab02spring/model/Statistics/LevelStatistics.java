package ch.usi.si.bsc.sa4.lab02spring.model.Statistics;

import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.service.GamePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents all the statistics data for a specific level.
 */
public class LevelStatistics {
    private List<List<EAction>> data = new ArrayList<>();

    public List<List<EAction>> getAll_attempts() {
        return data;
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
