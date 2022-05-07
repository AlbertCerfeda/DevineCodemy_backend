package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The UserNotAllowedException Exception thrown
 * 	when a certain User lacks permissions to perform an action.
 * 	e.g User is not allowed to access a Level.
 */
public class UserNotAllowedException extends RuntimeException {
	/**
	 * Constructs a new UserNotAllowedException object
	 * with the given message.
	 * @param message the message to be displayed when
	 *                the Exception is thrown.
	 */
	public UserNotAllowedException(String message){
		super(message);
	}
}
