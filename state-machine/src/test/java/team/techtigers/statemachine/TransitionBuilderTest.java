package team.techtigers.statemachine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransitionBuilderTest {
    private StateMachine mockStateMachine;
    private TransitionBuilder<MockCondition> transitionBuilder;

    @BeforeEach
    void setUp() {
        mockStateMachine = mock(StateMachine.class);
        transitionBuilder = new TransitionBuilder<>(mockStateMachine, "initialState");
    }

    @Test
    @DisplayName("to sets the next state correctly")
    void toSetsNextStateCorrectly() {
        TransitionBuilder result = transitionBuilder.to("nextState");
        assertNotNull(result);
    }

    @Test
    @DisplayName("when without to throws IllegalStateException")
    void whenWithoutToThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> transitionBuilder.when(MockCondition.CONDITION_MET));
    }

    @Test
    @DisplayName("when after to adds condition correctly")
    void whenAfterToAddsConditionCorrectly() {
        transitionBuilder.to("nextState").when(MockCondition.CONDITION_MET);
        verify(mockStateMachine).addCondition(eq("initialState"), any(Transition.class));
    }

    enum MockCondition {
        CONDITION_MET
    }
}