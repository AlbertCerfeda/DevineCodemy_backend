package ch.usi.si.bsc.sa4.lab02spring.controller.dto;
import  ch.usi.si.bsc.sa4.lab02spring.model.User.User;

/**
 * The stripped down state of a User object.
 */
public class UserDTO {
    private String id;
    private String name;
    
    public UserDTO(User user) {
        this.id   = user.getId();
        this.name = user.getName();
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
}
