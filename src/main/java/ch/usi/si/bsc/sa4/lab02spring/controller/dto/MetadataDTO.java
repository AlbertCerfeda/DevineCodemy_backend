package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.Metadata.Metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetadataDTO {
    private final String id;
    private final String username;
    private final HashMap level_data;

    public MetadataDTO(Metadata metadata){
        this.id = metadata.getId();
        this.username = metadata.getUsername();
        this.level_data = metadata.getData();

    }

    public String getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public HashMap getData(){
        return this.level_data;
    }

}
