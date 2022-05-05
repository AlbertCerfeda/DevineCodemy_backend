package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

public class UserInexistentException extends RuntimeException {
	public UserInexistentException() {
		super("User does not exist !");
	}
	
	public UserInexistentException(String userId){
		super("User '"+userId+"' does not exist !");
	}
}