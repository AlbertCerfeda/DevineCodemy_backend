package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The StatisticsInexistentException class represents the Exception thrown
 * 	when statistics for a certain level do not exist.
 */
public class StatisticInexistentException extends RuntimeException {
	/**
	 * Constructs a new StatisticsInexistentException object on a general case
	 * for a stat that does not exist.
	 */
	public StatisticInexistentException() {
		super("Level does not exist !");
	}

}
