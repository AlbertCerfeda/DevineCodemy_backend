package ch.usi.si.bsc.sa4.lab02spring.model.User;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
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
    
    private boolean publicProfile = false;
    
    /* TODO: Add additional fields
    - Statistics on completed levels
     */

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isProfilePublic() {
        return publicProfile;
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this);
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

}
