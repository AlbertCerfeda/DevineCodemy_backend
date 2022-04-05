package ch.usi.si.bsc.sa4.lab02spring.model.User;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.service.PasswordHashingService;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User {
    @Id
    private String id;
    private final String name;
    private final String email;
    private final String username;
    private String hash;
    
    private boolean publicProfile = false;
    
    /* TODO: Add additional fields and remove hash and anything related
    - GitLab specific fields
    - Statistics on completed levels
     */

    /**
     * Default constructor. Needed by ObjectMapper.readValue() to avoid failures
     * on the base case as it doesn't know whether the JSON retrieved contains
     * the required fields.
     */
    public User() {
        this.id = null;
        this.email = "";
        this.username = "";
        this.name = "";
    }


    /**
     * Main constructor to create the User with GitLab data.
     * @param id User's id (in GitLab)
     * @param name User's name
     * @param username User's GitLab username (unique)
     * @param email User's email
     */
    @PersistenceConstructor
    public User(String id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

//    @PersistenceConstructor
    public User(String id, String name, String hash) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.username = ""; //final fields can't be null, this constructor shouldn't exist
        this.email = ""; //final fields can't be null, this constructor shouldn't exist
    }

    public User(String name, String hash) {
        this.name = name;
        this.hash = hash;
        this.username = ""; //final fields can't be null, this constructor shouldn't exist
        this.email = ""; //final fields can't be null, this constructor shouldn't exist
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public Boolean isProfilePublic() { return publicProfile; }

    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword == null || newPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (!PasswordHashingService.getInstance().passwordMatches(oldPassword, this.hash)) {
            // This should be a domain specific exception (WrongPasswordException)
            throw new IllegalArgumentException("Wrong password.");
        }
        // You can do some checks on the validity here
        this.hash = PasswordHashingService.getInstance().hashPassword(newPassword);
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this);
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

}
