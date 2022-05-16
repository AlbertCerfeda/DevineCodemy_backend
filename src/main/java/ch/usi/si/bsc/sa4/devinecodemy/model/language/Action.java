package ch.usi.si.bsc.sa4.devinecodemy.model.language;


public abstract class Action implements LanguageBlock {

    // The next action in the chain, null if this is the last action
    protected final Action next;

    protected Action(Action next) {
        this.next = next;
    }

    public abstract void execute(Context context);

    public Action getNext() {
        return next;
    }

    protected void executeNextAction(Context context) {
        if (next != null) {
            next.execute(context);
        }
    }


}
