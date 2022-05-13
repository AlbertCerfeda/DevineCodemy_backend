package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class Action implements LanguageBlock {

    // The next action in the chain, null if this is the last action
    protected final Action next;

    @JsonCreator
    protected Action(Action next) {
        this.next = next;
    }

    public abstract void execute(Context context);

    protected void executeNextAction(Context context) {
        if (next != null) {
            next.execute(context);
        }
    }


}
