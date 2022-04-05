package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

public class CreateUserDTO {
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
