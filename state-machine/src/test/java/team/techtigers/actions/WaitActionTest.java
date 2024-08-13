package team.techtigers.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WaitActionTest {

    private WaitAction waitAction;

    @BeforeEach
    void setUp() {
        waitAction = new WaitAction(100);
    }

    @Test
    @DisplayName("Constructor initializes with correct duration")
    void constructorInitializesWithCorrectDuration() {
        WaitAction action = new WaitAction(500);
        assertFalse(action.isFinished());
    }

    @Test
    @DisplayName("Start method initializes start time")
    void startMethodInitializesStartTime() {
        waitAction.start();
        assertFalse(waitAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns true after duration")
    void isFinishedMethodReturnsTrueAfterDuration() throws InterruptedException {
        waitAction.start();
        Thread.sleep(150);
        waitAction.update();
        assertTrue(waitAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false before duration")
    void isFinishedMethodReturnsFalseBeforeDuration() throws InterruptedException {
        waitAction.start();
        Thread.sleep(50);
        waitAction.update();
        assertFalse(waitAction.isFinished());
    }
}