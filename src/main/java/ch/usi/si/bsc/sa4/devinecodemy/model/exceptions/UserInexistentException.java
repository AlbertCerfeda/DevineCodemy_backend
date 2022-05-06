package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The UserInexistentException class represents the Exception thrown
 * when searching for a User that doesn't exist.
 */
public class UserInexistentException extends RuntimeException {
	/**
	 * Constructs a new UserInexistentException object
	 * on a general case.
	 */
	public UserInexistentException() {
		super("User does not exist !");
	}

	/**
	 * Construct a new UserInexistentException object
	 * stating that the id with the given id doesn't exist.
	 * @param userId the id of the User not existing
	 */
	public UserInexistentException(String userId){
		super("User '"+userId+"' does not exist !");
	}
}