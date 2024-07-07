package team.techtigers.statemachine;

import java.util.function.BooleanSupplier;

public class TransitionBuilder {
    private final StateMachine stateMachine;
    private final String currentState;
    private BooleanSupplier condition;

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
     * @return the transition builder to allow for method chaining
     */
    public TransitionBuilder when(BooleanSupplier condition) {
        this.condition = condition;

        return this;
    }

    /**
     * Adds the next state to the transition, which is then added to the state machine
     *
     * @param nextState the name of the next state
     * @return the state machine to allow for method chaining
     */
    public StateMachine to(String nextState) {
        if (condition == null) {
            throw new IllegalStateException("Condition must be set (use when)");
        }

        stateMachine.addCondition(currentState, new Transition(condition, nextState));

        return stateMachine;
    }
}
