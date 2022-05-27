package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * LanguageBlock is the base class for all language blocks.
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=ActionMoveForward.class, name = "moveForward"),
        @JsonSubTypes.Type(value=ActionTurnLeft.class, name = "turnLeft"),
        @JsonSubTypes.Type(value=ActionTurnRight.class, name = "turnRight"),
        @JsonSubTypes.Type(value=ActionCollectCoin.class, name = "collectCoin"),
        @JsonSubTypes.Type(value=ActionActivateLever.class, name = "pullLever"),
        @JsonSubTypes.Type(value=ActionIf.class, name = "if"),
        @JsonSubTypes.Type(value=ActionIfElse.class, name = "ifElse"),
        @JsonSubTypes.Type(value=ActionLoop.class, name = "loop"),
        @JsonSubTypes.Type(value=ActionWhile.class, name = "while"),
        @JsonSubTypes.Type(value=ActionFunctionCall.class, name = "functionCall"),
        @JsonSubTypes.Type(value=FunctionDefinition.class, name = "functionDefinition"),
})
public interface LanguageBlock {

}
