package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The UserNotAllowedException Exception thrown
 * 	when a certain User lacks permissions to perform an action.
 * 	e.g User is not allowed to access a Level.
 * 	@throws UserNotAllowedException
 */

public class UserNotAllowedException extends RuntimeException {

	/**
	 * Constructs a new UserNotAllowedException object
	 * with the given message.
	 * @param message the message to be displayed when
	 *                the Exception is thrown.
	 * @throws UserNotAllowedException
	 */

	public UserNotAllowedException(String message){
		super(message);
	}
	
	/**
	 * Constructs a new UserNotAllowedException regarding
	 * a user not being able to access a specific level
	 * @param userId teh ID of the user.
	 * @param levelNumber the number of the level the user
	 *                    does not have access to.
	 * @throws UserNotAllowedException
	 */
	public UserNotAllowedException(String userId, int levelNumber){
		super("User '"+userId+"' is not allowed to access level #"+levelNumber+" !");
	}
}
