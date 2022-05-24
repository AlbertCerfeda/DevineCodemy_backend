package ch.usi.si.bsc.sa4.devinecodemy.model.language;

/**
 * Boolean Operation represents a class for valuating BooleanCondition objects
 * Supports AND,OR and NOT conditions
 */

public interface BooleanOperation {

    /**
     * Compute a boolean operation
     * 
     * @param bool1 the first boolean
     * @param bool2 the second boolean
     * @return the boolean result of a boolean operation
     *
     */
    boolean evaluate(BooleanCondition bool1, BooleanCondition bool2);
}
