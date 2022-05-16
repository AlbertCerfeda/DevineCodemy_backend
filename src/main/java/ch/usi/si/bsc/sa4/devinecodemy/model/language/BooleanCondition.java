package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ConditionContainsCoin.class, name = "containsCoin"),
        @JsonSubTypes.Type(value=ConditionCanStep.class, name = "canStep"),
        @JsonSubTypes.Type(value=ConditionFalse.class, name = "false"),
        @JsonSubTypes.Type(value=ConditionTrue.class, name = "true"),
})
public interface BooleanCondition {

    boolean evaluate(Context context);
}
