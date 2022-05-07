package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * InvalidAuthTokenException class represents the Exception
 * thrown when an Invalid auth token has been given.
 */
public class InvalidAuthTokenException extends RuntimeException {
	/**
	 * Instantiates a new InvalidAuthTokenException object.
	 */
	public InvalidAuthTokenException() {
		super("Invalid auth token !");
	}
}
