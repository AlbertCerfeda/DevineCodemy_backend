package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionDefinition implements LanguageBlock {

    private final String name;
    private final Action body;

    @JsonCreator
    public FunctionDefinition(@JsonProperty("name") final String name,
                              @JsonProperty("body") Action body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public Action getBody() {
        return body;
    }
}
