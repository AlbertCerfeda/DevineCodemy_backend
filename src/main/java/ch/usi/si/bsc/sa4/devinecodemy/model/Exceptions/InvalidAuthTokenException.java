package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class InvalidAuthTokenException extends RuntimeException {
	public InvalidAuthTokenException() {
		super("Invalid auth token !");
	}
}
