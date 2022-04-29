package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String message){
		super(message);
	}
}
