package ch.usi.si.bsc.sa4.devinecodemy.model.exceptions;

/**
 * The ExecutionTimeoutException class represents the Exception thrown
 * 	when an execution takes too long (typically occurs on loops).
 * @throws ExecutionTimeoutException
 */
public class ExecutionTimeoutException extends RuntimeException {

    /**
     * Constructs a new ExecutionTimeoutException object on timeout.
     * @throws ExecutionTimeoutException
     */
    public ExecutionTimeoutException() {
        super("Method execution timed out. " +
                "It seems that there is an infinite loop in your code. " +
                "We support a maximum of 1000 iterations.");
    }
}
