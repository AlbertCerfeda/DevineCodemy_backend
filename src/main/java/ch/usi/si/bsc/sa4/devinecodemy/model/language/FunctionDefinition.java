package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a function definition.
 * A function definition consists of a name and an Action
 * to execute whe the function is called.
 */
public class FunctionDefinition implements LanguageBlock {

    private final String name;
    private final Action body;

    /**
     * Creates a new FunctionDefinition.
     * @param name the name of the function
     * @param body the body of the function
     */
    @JsonCreator
    public FunctionDefinition(@JsonProperty("functionName") final String name,
                              @JsonProperty("body") Action body) {
        this.name = name;
        this.body = body;
    }

    /**
     * Returns the name of the function.
     * @return the name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the body of the function.
     * @return the body of the function
     */
    public Action getBody() {
        return body;
    }
}
