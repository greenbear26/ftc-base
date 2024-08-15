package team.techtigers.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParallelActionTest {

    private IAction mockAction1;
    private IAction mockAction2;
    private IAction mockAction3;

    @BeforeEach
    void setUp() {
        mockAction1 = mock(IAction.class);
        mockAction2 = mock(IAction.class);
        mockAction3 = mock(IAction.class);
    }

    @Test
    @DisplayName("Constructor throws exception for null actions array")
    void constructorThrowsExceptionForNullActionsArray() {
        assertThrows(IllegalArgumentException.class, () -> new ParallelAction(null));
    }

    @Test
    @DisplayName("Constructor throws exception for null action in array")
    void constructorThrowsExceptionForNullActionInArray() {
        IAction[] actions = {mockAction1, null, mockAction2};
        assertThrows(IllegalArgumentException.class, () -> new ParallelAction(actions));
    }

    @Test
    @DisplayName("Start method calls start on all actions")
    void startMethodCallsStartOnAllActions() {
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        ParallelAction parallelAction = new ParallelAction(actions);
        parallelAction.start();
        verify(mockAction1, times(1)).start();
        verify(mockAction2, times(1)).start();
        verify(mockAction3, times(1)).start();
    }

    @Test
    @DisplayName("Update method calls update on all unfinished actions")
    void updateMethodCallsUpdateOnAllUnfinishedActions() {
        when(mockAction1.isFinished()).thenReturn(false);
        when(mockAction2.isFinished()).thenReturn(true);
        when(mockAction3.isFinished()).thenReturn(false);

        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        ParallelAction parallelAction = new ParallelAction(actions);
        parallelAction.update();
        verify(mockAction1, times(1)).update();
        verify(mockAction2, never()).update();
        verify(mockAction3, times(1)).update();
    }

    @Test
    @DisplayName("IsFinished method returns true when all actions are finished")
    void isFinishedMethodReturnsTrueWhenAllActionsAreFinished() {
        when(mockAction1.isFinished()).thenReturn(true);
        when(mockAction2.isFinished()).thenReturn(true);
        when(mockAction3.isFinished()).thenReturn(true);

        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        ParallelAction parallelAction = new ParallelAction(actions);
        assertTrue(parallelAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false when any action is not finished")
    void isFinishedMethodReturnsFalseWhenAnyActionIsNotFinished() {
        when(mockAction1.isFinished()).thenReturn(true);
        when(mockAction2.isFinished()).thenReturn(false);
        when(mockAction3.isFinished()).thenReturn(true);

        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        ParallelAction parallelAction = new ParallelAction(actions);
        assertFalse(parallelAction.isFinished());
    }
}