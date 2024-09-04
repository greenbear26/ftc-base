package team.techtigers.statemachine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import team.techtigers.core.utils.GlobalState;

class GlobalStateTest {

    @Test
    @DisplayName("GlobalState initializes with current time as startTime")
    void globalStateInitializesWithCurrentTime() {
        GlobalState globalState = new GlobalState();
        assertTrue(System.currentTimeMillis() >= globalState.startTime);
    }

    @Test
    @DisplayName("getRunTime returns positive time after initialization")
    void getRunTimeReturnsPositiveTimeAfterInitialization() throws InterruptedException {
        GlobalState globalState = new GlobalState();
        Thread.sleep(1); // Ensure time has passed
        assertTrue(globalState.getRunTime() > 0);
    }

    @Test
    @DisplayName("getRunTime increases over time")
    void getRunTimeIncreasesOverTime() throws InterruptedException {
        GlobalState globalState = new GlobalState();
        long initialRunTime = globalState.getRunTime();
        Thread.sleep(1); // Ensure time has passed
        long laterRunTime = globalState.getRunTime();
        assertTrue(laterRunTime > initialRunTime);
    }
}