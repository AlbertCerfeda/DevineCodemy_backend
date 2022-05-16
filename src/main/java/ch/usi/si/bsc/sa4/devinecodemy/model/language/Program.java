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
 * This is an example of JSON program
 * program: {
 *   commands: [
 *     {
 *       type: 'while',
 *       condition: {
 *         type: 'canStep'
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
 *
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
 *
 */




public class Program {

    private final List<LanguageBlock> blocks;

    @JsonCreator
    public Program(@JsonProperty("commands") List<LanguageBlock> blocks) {
        this.blocks = blocks;
    }


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
        if (actionCount == 0) {
            levelValidation.addError("Nothing to execute");
        }

        // do not execute the program if there are errors in the parsing
        if (levelValidation.hasErrors() || main == null) {
            levelValidation.setCompleted(false);
            levelValidation.clearAnimations();
            levelValidation.addAnimation(EAnimation.EMOTE_DEATH);
            return levelValidation;
        }

        // set the function table in the context
        context.setFunctionTable(functionTable);

        // execute the main action
        main.execute(context);

        // if there are errors in the execution, set the level as failed
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
