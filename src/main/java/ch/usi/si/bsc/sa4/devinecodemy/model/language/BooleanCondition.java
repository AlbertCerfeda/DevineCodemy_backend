package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * A condition that can be evaluated to a boolean value.
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ConditionContainsCoin.class, name = "containsCoin"),
        @JsonSubTypes.Type(value=ConditionCanStep.class, name = "canStep"),
        @JsonSubTypes.Type(value=ConditionFalse.class, name = "false"),
        @JsonSubTypes.Type(value=ConditionTrue.class, name = "true"),
})
public interface BooleanCondition {


    /**
     * Evaluates the condition.
     *
     * @return the boolean value of the condition
     */
    boolean evaluate(Context context);
}
