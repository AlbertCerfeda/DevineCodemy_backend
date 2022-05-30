package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Represents an action function call in the language.
 */
public class ActionFunctionCall extends Action {

    private final String functionName;

    /**
     * Creates a new action function call.
     * @param functionName the name of the function to call.
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionFunctionCall(@JsonProperty("functionName") final String functionName,
                              @JsonProperty("next") final Action nextAction) {
        super(nextAction);
        this.functionName = functionName;
    }


    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        // look up the function in the block list and execute it
        final Map<String, Action> functionTable = context.getFunctionTable();
        if (functionTable.containsKey(functionName)) {
            // execute the function
            functionTable.get(functionName).execute(context);
        } else {
            context.getLevelValidation().addError("Function " + functionName + " not found");
        }

        // execute the next action, even if the function
        // is not found (to look for other errors in the code)
        super.executeNextAction(context);
    }
}
