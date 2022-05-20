package ch.usi.si.bsc.sa4.devinecodemy.model;


import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;
import org.springframework.data.util.Pair;

/**
 * The worlds which a level can belong to.
 */
public enum EWorld {

    PURGATORY("Purgatory", 1,
            "<h1>Let's start programming!</h1>" +
                    "<p>If you are here, I suppose you have interest in coding, or at least you want to discover the " +
                    "magical world of programming.<br>Of course, before running we have to learn how to walk. " +
                    "In this world, you are going to learn about basic commands, and use them in order for" +
                    " <b>RoboDante</b>, the main character of Devine Codemy, to reach Paradise <br><br><br>" +
                    " In this world you'll have the possibility to use the following " +
                    "commands:</p> <ul><li>moveForward</li><li>turnLeft</li>" +
                    "<li>turnRight</li><li>collectCoin</li></ul><br><p>Are you ready? Let's go!</p>"
            
            , "Congratulations ! You just completed the purgatory world and reached paradise ! " +
            "<br> In this series of levels, you learnt how to use the various basic commands." +
            "<br><br><br> Be prepared for what's next! "),
    PARADISE("Paradise", 2,
            "<h1>Conditionals</h1><p>Now things get more interesting. Conditionals are the bread " +
                    "and butter in programming, so make sure to learn them !</p><br>" +
                    "<p> In the previous world, you had a list of commands, and you could choose and write them" +
                    " in a specific order to complete the level. With conditionals, you will have some code" +
                    " like this:<br><br>if ( //condition to check)  {<br><br> // if the condition is true, " +
                    "this block of code will be executed <br><br> } else {<br><br>// Otherwise, this block of " +
                    "code will be executed<br><br>}</p>",
            "Congratulations! You just completed the paradise world and reached the inferno. <br> " +
                    "In this series of levels, you learnt how to use conditionals in order to complete " +
                    "the tasks.<br><br><br> Be prepared for what's next! "),
    INFERNO("Inferno", 3,
            "Sample description for the Inferno world !",
            "Congrats!");


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
