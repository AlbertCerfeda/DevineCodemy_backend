package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EActionDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    EARTH("Part of the earth levels"), //Example descriptions
    SKY("Part of the sky levels"),
    LAVA("Part of the lava levels");

    //Description of the world
    private final String description;

    EWorld(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public EWorldDTO toEWorldDTO() { return new EWorldDTO(this); }

}
