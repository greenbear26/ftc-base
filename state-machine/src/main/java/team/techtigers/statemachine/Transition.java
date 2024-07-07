package team.techtigers.statemachine;

import java.util.function.BooleanSupplier;

/**
 * Represents a condition that must be met for a state to transition to the next state
 */
public class Transition {
    private final BooleanSupplier endCondition;
    private final String nextState;

    /**
     * Initializes a new Transition
     *
     * @param endCondition the condition, returns true when the condition is met and the state should
     *                  transition
     * @param nextState the name of the next state
     */
    public Transition(BooleanSupplier endCondition, String nextState) {
        this.endCondition = endCondition;
        this.nextState = nextState;
    }

    /**
     * @return if the condition is met
     */
    public boolean isFinished() {
        return endCondition.getAsBoolean();
    }

    /**
     * @return the name of the next state
     */
    public String getNextState() {
        return nextState;
    }
}
