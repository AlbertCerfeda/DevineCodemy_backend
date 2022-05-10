package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;
import org.springframework.data.util.Pair;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    EARTH("Earth", 1, "Sample description for the EARTH world !", "Congrats!"), //Example descriptions
    SKY("Sky", 2, "Sample description for the SKY world !", "Congrats!"),
    LAVA("Lava", 3, "Sample description for the LAVA world !", "Congrats!");


    private final String displayName;
    private final int worldNumber;
    private final String descriptionMessage;
    private final String congratulationsMessage;

    /**
     * Constructor for the EWorld ENUM.
     * @param displayName            the name of the world
     *                               that will get displayed in the frontend.
     * @param descriptionMessage     the description of the topics covered by a world.
     * @param congratulationsMessage the congratulations message to display after
     *                               completing all the levels inside a world.
     */
    EWorld(final String displayName, final int worldNumber, final String descriptionMessage, final String congratulationsMessage) {
        this.displayName = displayName;
        this.worldNumber = worldNumber;
        this.descriptionMessage = descriptionMessage;
        this.congratulationsMessage = congratulationsMessage;
    }

    /**
     * Create a new EWorld from a world name.
     * Example: "LAVA" -> EWorld.LAVA
     *
     * @param world the given world
     * @throws IllegalArgumentException if the string does not correspond
     *                                  to a valid EWorld value.
     */
    public static EWorld getEWorldFromString(String world) throws IllegalArgumentException {
        for (final EWorld eworld : EWorld.values()) {
            if (eworld.getDisplayName().equals(world)) {
                return eworld;
            }
        }


        throw new IllegalArgumentException("Unknown world: '" + world + "'");
    }

    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    public String getCongratulationsMessage() {
        return congratulationsMessage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getWorldNumber() {
        return worldNumber;
    }

    /**
     * Returns the EWorldDTO version of this object, with as number range
     * the first being the first of the Pair and the last being the
     * second of the Pair.
     * @param levelNumberRange the levelNumberRange of the EWorld.
     * @return the EWorldDTO object with the values of this.
     */
    public EWorldDTO toEWorldDTO(Pair<Integer, Integer> levelNumberRange) {
        return new EWorldDTO(this, levelNumberRange);
    }

}
