package team.techtigers.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SerialActionTest {

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
        assertThrows(IllegalArgumentException.class, () -> new SerialAction(null));
    }

    @Test
    @DisplayName("Constructor throws exception for null action in array")
    void constructorThrowsExceptionForNullActionInArray() {
        IAction[] actions = {mockAction1, null, mockAction2};
        assertThrows(IllegalArgumentException.class, () -> new SerialAction(actions));
    }

    @Test
    @DisplayName("Start method calls start on the first action")
    void startMethodCallsStartOnFirstAction() {
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        SerialAction serialAction = new SerialAction(actions);
        serialAction.start();
        verify(mockAction1, times(1)).start();
    }

    @Test
    @DisplayName("Update method calls update on the current action")
    void updateMethodCallsUpdateOnCurrentAction() {
        when(mockAction1.isFinished()).thenReturn(false);
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        SerialAction serialAction = new SerialAction(actions);
        serialAction.start();
        serialAction.update();
        verify(mockAction1, times(1)).update();
    }

    @Test
    @DisplayName("Update method starts the next action when current action is finished")
    void updateMethodStartsNextActionWhenCurrentActionIsFinished() {
        when(mockAction1.isFinished()).thenReturn(true);
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        SerialAction serialAction = new SerialAction(actions);
        serialAction.start();
        serialAction.update();
        verify(mockAction2, times(1)).start();
    }

    @Test
    @DisplayName("IsFinished method returns true when all actions are finished")
    void isFinishedMethodReturnsTrueWhenAllActionsAreFinished() {
        when(mockAction1.isFinished()).thenReturn(true);
        when(mockAction2.isFinished()).thenReturn(true);
        when(mockAction3.isFinished()).thenReturn(true);
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        SerialAction serialAction = new SerialAction(actions);
        serialAction.start();
        serialAction.update();
        serialAction.update();
        serialAction.update();
        assertTrue(serialAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false when any action is not finished")
    void isFinishedMethodReturnsFalseWhenAnyActionIsNotFinished() {
        when(mockAction1.isFinished()).thenReturn(true);
        when(mockAction2.isFinished()).thenReturn(false);
        IAction[] actions = {mockAction1, mockAction2, mockAction3};
        SerialAction serialAction = new SerialAction(actions);
        serialAction.start();
        serialAction.update();
        assertFalse(serialAction.isFinished());
    }
}