package ch.usi.si.bsc.sa4.lab02spring.model.Metadata;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.AddDataDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.MetadataDTO;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Metadata {
    @Id
    private String id;
    private String username;
    private HashMap<String,List<String[]>> level_data;


    public Metadata(){

    }

    public Metadata(String id, String username, HashMap<String,List<String[]>> level_data){
        this.id = id;
        this.username = username;
        this.level_data = level_data;
    }

    public Metadata(String username) {
        this.username = username;
        level_data = new HashMap<String,List<String[]>>();
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

    public void addData(String level_id, String[] data){

        if(level_data.get(level_id) != null){
            List<String[]> l = level_data.get(level_id);
            l.add(data);
            level_data.put(level_id,l);
        }else{
            List<String[]> n = new ArrayList<String[]>();
            n.add(data);
            level_data.put(level_id,n);
        }

    }

    public void createData(String level_id, String[] data){
            List<String[]> n = new ArrayList<String[]>();
            n.add(data);
            level_data.put(level_id,n);
    }

    public static void updateData(Metadata meta, AddDataDTO data){
        meta.addData(data.getLevel_name(), data.getData());
    }

    public MetadataDTO toMetadataDTO(){
        return new MetadataDTO(this);
    }



}


