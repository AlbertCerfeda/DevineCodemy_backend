package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The LevelInexistentException class represents the Exception
 * thrown when trying to use a not existing Level.
 */
public class LevelInexistentException extends RuntimeException {
	/**
	 * Constructs a new LevelInexistentException object on a general case
	 * of a not existing level.
	 */
	public LevelInexistentException() {
		super("Level does not exist !");
	}

	/**
	 * Constructs a new LevelInexistentException object stating
	 * the level with the given levelNumber doesn't exist.
	 * @param levelNumber the levelNumber of the searched level.
	 */
	public LevelInexistentException(int levelNumber){
		super("Level #"+levelNumber+" does not exist !");
	}
}
