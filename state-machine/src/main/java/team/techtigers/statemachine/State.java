package team.techtigers.statemachine;

/**
 * Base class for all states, which are used to run a a step of a state machine
 * @param <T> The type of the condition, usually an enum
 */
public abstract class State<T> {
    /**
     * Starts the executor
     */
    public abstract void start();

    /**
     * Updates the executor
     */
    public abstract void update();

    /**
     * Ends the executor
     */
    public abstract void end();

    /**
     * @return The current condition of the state, usually an enum
     */
    public abstract T getCurrentCondition();
}
