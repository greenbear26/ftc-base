package team.techtigers.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.function.BooleanSupplier;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaitUntilActionTest {

    private BooleanSupplier mockCondition;

    @BeforeEach
    void setUp() {
        mockCondition = mock(BooleanSupplier.class);
    }

    @Test
    @DisplayName("Constructor initializes with non-null condition")
    void constructorInitializesWithNonNullCondition() {
        assertDoesNotThrow(() -> new WaitUntilAction(mockCondition));
    }

    @Test
    @DisplayName("Constructor throws exception for null condition")
    void constructorThrowsExceptionForNullCondition() {
        assertThrows(IllegalArgumentException.class, () -> new WaitUntilAction(null));
    }

    @Test
    @DisplayName("IsFinished method returns true when condition is met")
    void isFinishedMethodReturnsTrueWhenConditionIsMet() {
        when(mockCondition.getAsBoolean()).thenReturn(true);
        WaitUntilAction waitUntilAction = new WaitUntilAction(mockCondition);
        assertTrue(waitUntilAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false when condition is not met")
    void isFinishedMethodReturnsFalseWhenConditionIsNotMet() {
        when(mockCondition.getAsBoolean()).thenReturn(false);
        WaitUntilAction waitUntilAction = new WaitUntilAction(mockCondition);
        assertFalse(waitUntilAction.isFinished());
    }
}