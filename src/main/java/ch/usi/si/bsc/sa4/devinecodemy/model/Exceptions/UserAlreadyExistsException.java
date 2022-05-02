package ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {
    super("User already exists !");
  }

  public UserAlreadyExistsException(String userId) {
    super("User '" + userId + "' already exists !");
  }
}