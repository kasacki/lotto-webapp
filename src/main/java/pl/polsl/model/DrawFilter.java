package pl.polsl.model;

/**
 * Functional interface representing a filter for draw objects.
 *
 * <p>This interface is intended to be used with lambda expressions or method
 * references to provide flexible and customizable filtering logic for Lotto draws.
 *
 * <p>Example usage:
 * <pre>
 * DrawFilter filter = d -> d.numbers().contains(7);
 * </pre>
 *
 * This filter accepts only draws in which the number 7 appears.
 */
@FunctionalInterface
public interface DrawFilter {

    /**
     * Tests whether the given draw satisfies the filter condition.
     *
     * @param draw the Draw object to check
     * @return true if the draw matches the condition, false otherwise
     */
    boolean accepts(Draw draw);
}
