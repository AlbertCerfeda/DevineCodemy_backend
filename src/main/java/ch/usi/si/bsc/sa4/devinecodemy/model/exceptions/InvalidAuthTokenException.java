package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * InvalidAuthTokenException class represents the Exception
 * thrown when an Invalid auth token has been given.
 */
public class InvalidAuthTokenException extends RuntimeException {
	/**
	 * Constructs a new InvalidAuthTokenException object at Runtime.
	 */
	public InvalidAuthTokenException() {
		super("Invalid auth token !");
	}
}
