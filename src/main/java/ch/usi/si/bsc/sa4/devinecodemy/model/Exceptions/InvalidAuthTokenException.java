package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class InvalidAuthTokenException extends RuntimeException {
	public InvalidAuthTokenException(String message){
		super(message);
	}
}
