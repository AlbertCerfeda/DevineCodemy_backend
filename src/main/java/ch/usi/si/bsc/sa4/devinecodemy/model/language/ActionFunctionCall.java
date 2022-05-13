package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ActionFunctionCall extends Action {

    private final String functionName;

    @JsonCreator
    public ActionFunctionCall(@JsonProperty("functionName") final String functionName,
                              @JsonProperty("next") final Action nextAction) {
        super(nextAction);
        this.functionName = functionName;
    }


    @Override
    public void execute(Context context) {
        // look up the function in the block list and execute it
        Map<String, Action> functionTable = context.getFunctionTable();
        if (!functionTable.containsKey(functionName)) {
            context.getLevelValidation().addError("Function " + functionName + " not found");
        } else {
            // execute the function
            functionTable.get(functionName).execute(context);
        }

        // execute the next action, even if the function is not found (to look for other errors in the code)
        super.executeNextAction(context);
    }
}
