package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


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
     * @param context the context of the game, it contains all the info needed.
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

        // execute the main
        executeParsedProgram(main, levelValidation, context, functionTable);

        // if there are errors in the parsing or during the execution, set the level as failed
        if (levelValidation.hasErrors()) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(ERobotAnimation.EMOTE_DEATH);
            return levelValidation;
        }


        // level completed
        if (context.getCollectedCoins() == context.getBoard().getCoinsNumber()) { // level completed
            levelValidation.addAnimation(ERobotAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        }
        return levelValidation;
    }


    /**
     * Executes the parsed program only if it is valid. Otherwise, adds Error
     * to the given level validation.
     * @param main the main Action in the program.
     * @param levelValidation the result of the validation.
     * @param context the context of the game, it contains all the info needed.
     * @param functionTable map from name of the functions to the equivalent
     *                      Action to be executed.
     */
    private void executeParsedProgram(Action main, LevelValidation levelValidation, Context context, Map<String, Action> functionTable) {

        if (main == null) {
            levelValidation.addError("No executable block in the program");
            return;
        }
        AtomicInteger actionsCount = new AtomicInteger(main.countActions());
        functionTable.forEach((name, action) ->
            actionsCount.addAndGet(action.countActions())
        );

        // if there is no action, error
        if (actionsCount.get() > context.getMaxCommandsNumber()) {
            levelValidation.addError("Too many commands in the program, we can only have " +
                                      context.getMaxCommandsNumber() + " while there are " + actionsCount.get());
        } else if (!levelValidation.hasErrors()) {
            // set the function table in the context
            context.setFunctionTable(functionTable);
            // execute the main action that cannot be null anymore
            try {
                main.execute(context);
            } catch (RuntimeException e) {
                levelValidation.addError(e.getMessage());
            }
        }
    }


    public List<LanguageBlock> getBlocks() {
        return blocks;
    }
}
