package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class UserInexistentException extends RuntimeException {
	public UserInexistentException(String message){
		super(message);
	}
}
