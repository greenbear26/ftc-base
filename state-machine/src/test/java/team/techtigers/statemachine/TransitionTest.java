package team.techtigers.statemachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransitionTest {

    @Test
    @DisplayName("Transition is finished when current condition matches end condition")
    void transitionIsFinishedWhenConditionMatches() {
        Transition<MockCondition> transition = new Transition<>(MockCondition.CONDITION_MET, "nextState");
        assertTrue(transition.isFinished(MockCondition.CONDITION_MET));
    }

    @Test
    @DisplayName("Transition is not finished when current condition does not match end condition")
    void transitionIsNotFinishedWhenConditionDoesNotMatch() {
        Transition<MockCondition> transition = new Transition<>(MockCondition.CONDITION_MET, "nextState");
        assertFalse(transition.isFinished(MockCondition.CONDITION_NOT_MET));
    }

    @Test
    @DisplayName("getNextState returns the correct next state")
    void getNextStateReturnsCorrectState() {
        Transition<MockCondition> transition = new Transition<>(MockCondition.CONDITION_MET, "nextState");
        assertEquals("nextState", transition.getNextState());
    }
}

enum MockCondition {
    CONDITION_MET,
    CONDITION_NOT_MET
}