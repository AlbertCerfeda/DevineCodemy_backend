package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The LevelInexistentException class represents the Exception thrown
 * 	when a certain level does not exist.
 * @throws LevelInexistentException
 */

public class LevelInexistentException extends RuntimeException {
	/**
	 * Constructs a new LevelInexistentException object on a general case
	 * for a level that does not exist.
	 * @thrwos LevelInexistentException
	 */

	public LevelInexistentException() {
		super("Level does not exist !");
	}


	/**
	 * Constructs a new LevelInexistentException object for
	 * 	a level with a given levelNumber.
	 * @param levelNumber the levelNumber of the level.
	 * @throws LevelInexistentException
	 */

	public LevelInexistentException(int levelNumber){
		super("Level #"+levelNumber+" does not exist !");
	}
}
