package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

public class AddDataDTO {
    private String username;
    private String level_name;
    private String[] data;

    public AddDataDTO() {}

    public AddDataDTO(String username,String level_name, String[] data) {
        this.username = username;
        this.level_name = level_name;
        this.data = data;
    }

    public String getUsername() {return username;}

    public String[] getData() { return data;}

    public String getLevel_name() {return level_name;}

}
