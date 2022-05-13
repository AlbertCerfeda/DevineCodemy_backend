package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This is an example of JSON program
 * {
 *     commands: [
 *      {
 *          name: "moveForward",
 *          next: {
 *              name: "collectCoin",
 *              next: {
 *                  name: "functionCall",
 *                  functionName: "rotateLeft"
 *                  next: null,
 *              }
 *          }
 *      },
 *      {
 *          name: "functionDefinition",
 *          functionName: "rotateLeft",
 *          body: {
 *              name: "turnLeft",
 *              next: null,
 *          }
 *      },
 *      {
 *          name: "functionDefinition",
 *          functionName: "rotate180Left",
 *          body: {
 *              name: "turnLeft",
 *              next: {
 *                  name: "turnLeft",
 *                  next: null
 *              },
 *          }
 *      },
 *
 *     ]
 * }
 */
public class Program {

    private final List<LanguageBlock> blocks;

    @JsonCreator
    public Program(@JsonProperty("commands") final List<LanguageBlock> blocks) {
        this.blocks = blocks;
    }


    public LevelValidation execute(Context context) {


        Map<String, Action> functionTable = new HashMap<>();

        // Scan all blocks, if there is more than one Action: error, cannot execute the program
        // create a function table with all the functionDefinitions, mapping the functionName to the Action body to execute
        Action main = null;
        int actionCount = 0;
        for (LanguageBlock block : blocks) {
            if (block instanceof Action) {
                if (actionCount > 0) {
                    context.getLevelValidation().addError("More than one action in the program"); // TODO: write better error message
                }
                actionCount++;
                main = (Action)block;
            } else if (block instanceof FunctionDefinition) {
                FunctionDefinition functionDefinition = (FunctionDefinition)block;
                // if the key is already in the table, error
                if (functionTable.containsKey(functionDefinition.getName())) {
                    context.getLevelValidation().addError("Function " + functionDefinition.getName() + " is already defined");
                    return context.getLevelValidation();
                }
                // TODO: check also if the function name is basic one (e.g. moveForward) ???
                functionTable.put(functionDefinition.getName(), functionDefinition.getBody());
            }
        }

        // if there is no action, error
        if (actionCount == 0) {
            context.getLevelValidation().addError("Nothing to execute"); // TODO: write better error message
        }

        context.setFunctionTable(functionTable);
        // execute the main action
        assert main != null;
        main.execute(context);


        // TODO: check if the coins are all collected, ...
        // TODO: add final animations, ...
        LevelValidation levelValidation = context.getLevelValidation();
        if (context.getCollectedCoins() == context.getBoard().getCoinsNumber()) { // level completed
            levelValidation.addAnimation(EAnimation.EMOTE_DANCE);
            levelValidation.setCompleted(true);
        }

        return context.getLevelValidation();
    }


}
