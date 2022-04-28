package ch.usi.si.bsc.sa4.devinecodemy.model.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserDTO;

@Document(collection="users")
public class User {
    @Id
    private final String id;
    private final String name;
    private final String email;
    private final String username;
    private final String avatarUrl;
    private boolean publicProfile = false;
    private String bio;
    

    /**
     * Main constructor to create the User with GitLab data.
     * @param id User's id (in GitLab)
     * @param name User's name
     * @param username User's GitLab username (unique)
     * @param email User's email
     */
    @PersistenceConstructor
    public User(String id, String name, String username, String email, String avatarUrl, String bio) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public String getAvatarUrl() { return avatarUrl; }

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

}
