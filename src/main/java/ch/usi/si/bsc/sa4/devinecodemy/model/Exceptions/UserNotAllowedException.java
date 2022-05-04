package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class UserNotAllowedException extends RuntimeException {
	public UserNotAllowedException(String message){
		super(message);
	}
}
