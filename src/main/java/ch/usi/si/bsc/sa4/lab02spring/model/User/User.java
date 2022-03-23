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
    private String hash;
    
    private boolean public_profile = false;
    
    /* TODO: Add additional fields
    - GitLab specific fields
    - Statistics on completed levels
     */

    @PersistenceConstructor
    public User(String id, String name, String hash) {
        this.id = id;
        this.name = name;
        this.hash = hash;
    }

    public User(String name, String hash) {
        this.name = name;
        this.hash = hash;
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

    public Boolean isProfilePublic() { return public_profile; }

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
}
