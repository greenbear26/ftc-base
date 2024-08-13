package team.techtigers.actions;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServoActionTest {

    private Servo mockServo;
    private ElapsedTime mockElapsedTime;

    @BeforeEach
    void setUp() {
        mockServo = mock(Servo.class);
        mockElapsedTime = mock(ElapsedTime.class);
    }

    @Test
    @DisplayName("Constructor throws exception for null servo")
    void constructorThrowsExceptionForNullServo() {
        assertThrows(IllegalArgumentException.class, () -> new ServoAction(null, 0.5, 1000));
    }

    @Test
    @DisplayName("Constructor throws exception for negative duration")
    void constructorThrowsExceptionForNegativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> new ServoAction(mockServo, 0.5, -1000));
    }

    @Test
    @DisplayName("Constructor throws exception for invalid position")
    void constructorThrowsExceptionForInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> new ServoAction(mockServo, 1.5, 1000));
        assertThrows(IllegalArgumentException.class, () -> new ServoAction(mockServo, -0.5, 1000));
    }

    @Test
    @DisplayName("Start method initializes correctly")
    void startMethodInitializesCorrectly() {
        when(mockServo.getPosition()).thenReturn(0.0);
        ServoAction servoAction = new ServoAction(mockServo, 1.0, 1000);
        servoAction.start();
        verify(mockServo, times(1)).getPosition();
    }

    @Test
    @DisplayName("Update method sets servo position correctly")
    void updateMethodSetsServoPositionCorrectly() throws InterruptedException {
        when(mockServo.getPosition()).thenReturn(0.0);
        ServoAction servoAction = new ServoAction(mockServo, 0.5, 100);
        servoAction.start();
        Thread.sleep(100);
        servoAction.update();
        verify(mockServo, times(1)).setPosition(0.5);
    }

    @Test
    @DisplayName("IsFinished method returns true when duration is exceeded")
    void isFinishedMethodReturnsTrueWhenDurationExceeded() throws InterruptedException {
        ServoAction servoAction = new ServoAction(mockServo, 1.0, 100);
        servoAction.start();
        Thread.sleep(150);
        servoAction.update();
        assertTrue(servoAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false when duration is not exceeded")
    void isFinishedMethodReturnsFalseWhenDurationNotExceeded() {
        when(mockElapsedTime.milliseconds()).thenReturn(500.0);
        ServoAction servoAction = new ServoAction(mockServo, 1.0, 1000);
        servoAction.start();
        assertFalse(servoAction.isFinished());
    }
}