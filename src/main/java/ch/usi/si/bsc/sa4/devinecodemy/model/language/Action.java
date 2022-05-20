package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;

/**
 * Represents an action in the language.
 * An action has a pointer to the next action in the program.
 * An action can be executed.
 */
public abstract class Action implements LanguageBlock {

    /** The next action in the chain, null if this is the last action */
    protected final Action next;

    /**
     * Creates a new action with the given next action,
     * which is executed after this action.
     * @param next The next action in the chain, null if this is the last action.
     */
    protected Action(Action next) {
        this.next = next;
    }

    /**
     * To executes the action given the context.
     * This method modifies the context according to the action execution.
     * @param context The context to execute the action.
     * @throws RuntimeException if too many actions are
     * executed (used to stop endless loops).
     */
    public abstract void execute(Context context) throws ExecutionTimeoutException;

    /**
     * To count the number of actions in the chain.
     * @return The number of actions in the chain.
     */
    public int countActions() {
        return 1 + (next == null ? 0 : next.countActions());
    }

    /**
     * Executes the next action in the chain.
     * If there is no next action, this method does nothing.
     *
     * @param context The context to execute the action.
     */
    protected void executeNextAction(Context context) {
        if (next != null) {
            next.execute(context);
        }
    }


}
