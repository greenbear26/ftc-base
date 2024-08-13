package team.techtigers.actions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MotorActionTest {

    private DcMotor mockMotor;

    @BeforeEach
    void setUp() {
        mockMotor = mock(DcMotor.class);
    }

    @Test
    @DisplayName("Constructor throws exception for null motor")
    void constructorThrowsExceptionForNullMotor() {
        assertThrows(IllegalArgumentException.class, () -> new MotorAction(null, 0.5, 1000));
    }

    @Test
    @DisplayName("Constructor throws exception for negative duration")
    void constructorThrowsExceptionForNegativeDuration() {
        assertThrows(IllegalArgumentException.class, () -> new MotorAction(mockMotor, 0.5, -1000));
    }

    @Test
    @DisplayName("Constructor throws exception for invalid speed")
    void constructorThrowsExceptionForInvalidSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new MotorAction(mockMotor, 1.5, 1000));
        assertThrows(IllegalArgumentException.class, () -> new MotorAction(mockMotor, -1.5, 1000));
    }

    @Test
    @DisplayName("Update method sets motor power correctly")
    void updateMethodSetsMotorPowerCorrectly() {
        MotorAction motorAction = new MotorAction(mockMotor, 0.5, 1000);
        motorAction.update();
        verify(mockMotor, times(1)).setPower(0.5);
    }

    @Test
    @DisplayName("IsFinished method returns true when duration is exceeded")
    void isFinishedMethodReturnsTrueWhenDurationExceeded() throws InterruptedException {
        MotorAction motorAction = new MotorAction(mockMotor, 0.5, 100);
        Thread.sleep(150);
        assertTrue(motorAction.isFinished());
    }

    @Test
    @DisplayName("IsFinished method returns false when duration is not exceeded")
    void isFinishedMethodReturnsFalseWhenDurationNotExceeded() {
        MotorAction motorAction = new MotorAction(mockMotor, 0.5, 1000);
        assertFalse(motorAction.isFinished());
    }
}