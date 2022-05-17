package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class represents a program: is a list of commands. This is the main class of the language.
 * Here a JSON example is shown:
 * program: {
 *   commands: [
 *     {
 *       type: 'while',
 *       condition: {
 *         type: 'canStep'
 *         direction: 'FORWARD'
 *       },
 *       body: {
 *         type: 'moveForward',
 *       },
 *       next: {
 *         type: 'functionCall',
 *         functionName: 'win',
 *         next: null // optional next command, if null or not present, the program ends
 *       },
 *     },
 *     {
 *       type: 'functionDefinition',
 *       functionName: 'win',
 *       body: {
 *         type: 'collectCoin',
 *       },
 *     }
 *
 *   ]
 * }
 */
public class Program {

    private final List<LanguageBlock> blocks;

    /**
     * Constructor of the program.
     *
     * @param blocks the list of actions and function definition representing the program.
     */
    @JsonCreator
    public Program(@JsonProperty("commands") List<LanguageBlock> blocks) {
        this.blocks = blocks;
    }

    /**
     * To execute the program.
     *
     * @return a LevelValidation object representing the result of the execution.
     */
    public LevelValidation execute(Context context) {

        Map<String, Action> functionTable = new HashMap<>();
        LevelValidation levelValidation = context.getLevelValidation();

        // Scan all blocks, if there is more than one Action: error, cannot execute the program
        // create a function table with all the functionDefinitions, mapping the functionName to the Action body to execute
        Action main = null;
        int actionCount = 0;
        for (LanguageBlock block : blocks) {
            if (block instanceof Action) {
                if (actionCount > 0) { // only one action is allowed
                    levelValidation.addError("More than one executable block in the program, we can only have one");
                }
                actionCount++;
                main = (Action)block;
            } else if (block instanceof FunctionDefinition) {
                FunctionDefinition functionDefinition = (FunctionDefinition)block;

                // check if the function name is already defined
                if (functionTable.containsKey(functionDefinition.getName())) {
                    levelValidation.addError("Function " + functionDefinition.getName() + " is already defined");
                }

                // check if the function definition is valid
                if (Arrays.asList("moveForward", "TurnLeft", "TurnRight", "CollectCoin").contains(functionDefinition.getName())) {
                    levelValidation.addError("Function name " + functionDefinition.getName() + " is reserved");
                }
                functionTable.put(functionDefinition.getName(), functionDefinition.getBody());
            }
        }

        // if there is no action, error
        if (main == null) {
            levelValidation.addError("No executable block in the program");
        } else if (main.countActions() > context.getMaxCommandsNumber()) {
            levelValidation.addError("Too many commands in the program, we can only have " +
                                     context.getMaxCommandsNumber());
        } else if (!levelValidation.hasErrors()) {
            // set the function table in the context
            context.setFunctionTable(functionTable);
            // execute the main action that cannot be null anymore
            main.execute(context);
        }

        // if there are errors in the parsing or during the execution, set the level as failed
        if (levelValidation.hasErrors()) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
            return levelValidation;
        }


        // level completed
        if (context.getCollectedCoins() == context.getBoard().getCoinsNumber()) { // level completed
            levelValidation.addAnimation(EAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        }

        return levelValidation;
    }


}
