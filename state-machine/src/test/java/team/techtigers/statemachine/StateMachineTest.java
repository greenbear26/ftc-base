package team.techtigers.statemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    private StateMachine stateMachine;
    private State mockState;
    private Transition mockTransition;

    @BeforeEach
    void setUp() {
        stateMachine = new StateMachine();
        mockState = mock(State.class);
        when(mockState.getName()).thenReturn("mockState");
        mockTransition = mock(Transition.class);
    }

    @Test
    @DisplayName("Adding a state stores it correctly")
    void addStateStoresStateCorrectly() {
        stateMachine.addState(mockState);
        assertTrue(stateMachine.addState(mockState) instanceof StateMachine);
    }

    @Test
    @DisplayName("Setting the first state to a non-existent state throws IllegalArgumentException")
    void setFirstStateToNonExistentStateThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> stateMachine.setFirstState("nonExistentState"));
    }

    @Test
    @DisplayName("Setting the first state to an existing state does not throw exception")
    void setFirstStateToExistingState() {
        stateMachine.addState(mockState);
        assertDoesNotThrow(() -> stateMachine.setFirstState(mockState.getName()));
    }

    @Test
    @DisplayName("Starting without a first state set throws IllegalStateException")
    void startWithoutFirstStateSetThrowsException() {
        assertThrows(IllegalStateException.class, () -> stateMachine.start());
    }

    @Test
    @DisplayName("Starting with a first state set does not throw exception")
    void startWithFirstStateSet() {
        stateMachine.addState(mockState);
        stateMachine.setFirstState(mockState.getName());
        assertDoesNotThrow(() -> stateMachine.start());
    }

    @Test
    @DisplayName("Updating state machine transitions to next state on condition")
    void updateTransitionsToNextStateOnCondition() {
        State nextState = mock(State.class);
        when(nextState.getName()).thenReturn("nextState");

        when(mockTransition.isFinished(null)).thenReturn(true);
        when(mockTransition.getNextState()).thenReturn("nextState");

        stateMachine.addState(mockState)
                .addState(nextState)
                .addCondition(mockState.getName(), mockTransition);
        stateMachine.setFirstState(mockState.getName());

        stateMachine.start();

        stateMachine.update();

        verify(mockTransition, times(1)).isFinished(null);
        verify(mockState, times(1)).initialize();
        verify(nextState, times(1)).initialize();
    }

    @Test
    @DisplayName("Updating state machine without conditions does nothing")
    void updateWithoutConditionsDoesNothing() {
        stateMachine.addState(mockState)
                .setFirstState(mockState.getName());
        stateMachine.start();

        assertDoesNotThrow(() -> stateMachine.update());
    }
}