package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The UserNotAllowedException class represents the Exception
 * thrown when trying to execute an action that a User is not
 * allowed to execute.
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
