package team.techtigers.statemachine;

import java.util.function.BooleanSupplier;

public class TransitionBuilder {
    private final StateMachine stateMachine;
    private final String currentState;
    private String nextState;

    /**
     * Initializes a new TransitionBuilder
     *
     * @param stateMachine the state machine to add the transition to
     * @param currentState the name of the current state
     */
    public TransitionBuilder(StateMachine stateMachine, String currentState) {
        this.stateMachine = stateMachine;
        this.currentState = currentState;
    }

    /**
     * Adds a condition to the transition
     *
     * @param condition the condition that must be met for the state to transition
     * @return the state machine to allow for method chaining
     */
    public StateMachine when(BooleanSupplier condition) {
        if (nextState == null) {
            throw new IllegalStateException("Condition must be set (use to)");
        }

        stateMachine.addCondition(currentState, new Transition(condition, nextState));

        return stateMachine;
    }

    /**
     * Adds the next state to the transition
     *
     * @param nextState the name of the next state
     * @return the transition builder to allow for method chaining
     */
    public TransitionBuilder to(String nextState) {
        this.nextState = nextState;

        return this;
    }
}
