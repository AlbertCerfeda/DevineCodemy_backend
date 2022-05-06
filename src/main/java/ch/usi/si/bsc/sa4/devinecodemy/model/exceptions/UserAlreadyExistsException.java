package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The UserAlread.ExistsException class represents the Exception
 * thrown when trying to add a user that already exists/
 */
public class UserAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new UserAlreadyExistsException object
   * on a general case.
   */
  public UserAlreadyExistsException() {
    super("User already exists !");
  }

  /**
   * Constructs a UserAlreadyExistsException object
   * stating the User with the given id already exists.
   * @param userId the id of the user already existing.
   */
  public UserAlreadyExistsException(String userId) {
    super("User '" + userId + "' already exists !");
  }
}