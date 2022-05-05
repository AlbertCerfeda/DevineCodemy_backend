package ch.usi.si.bsc.sa4.devinecodemy.model;


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

}
