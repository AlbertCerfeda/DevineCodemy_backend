package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

/**
 * The create state of a User object.
 */
public class CreateUserDTO {
    private String id;
    private String name;
    private String username;
    private String email;

    private String avatar_url;

    /**
     * Default constructor. Needed by ObjectMapper.readValue() to avoid failures
     * on the base case as it doesn't know whether the JSON retrieved contains
     * the required fields.
     */
    public CreateUserDTO() {
    }

    public CreateUserDTO(String id, String name, String username, String email, String avatar_url) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar_url = avatar_url;
    }

    public String getAvatar_url() { return avatar_url; }

    public void setAvatar_url(String avatar_url) { this.avatar_url = avatar_url; }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
