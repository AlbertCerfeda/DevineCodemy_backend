package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;

/**
 * The stripped down state of a User object.
 */
public class UserDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    
    /**
     * Constructor of UserDTO.
     * @param user the User from which to retrieve the DTO data.
     * @param checkPrivate if true keeps only the essential data if the profile is private.
     */
    public UserDTO(User user, boolean checkPrivate) {
        this.id   = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        
        if(checkPrivate && !user.isProfilePublic()) {
            this.email = null;
        }
    }
    
    
    // Getters and setters
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
