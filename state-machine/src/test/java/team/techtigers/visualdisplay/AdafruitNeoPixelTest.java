package team.techtigers.visualdisplay;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import team.techtigers.core.leddisplay.Color;

class AdafruitNeoPixelTest {

    @Test
    @DisplayName("initialize sets up buffers correctly")
    void initializeSetsUpBuffersCorrectly() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        assertEquals(10, neoPixel.currentLedBuffer.length);
        assertEquals(10, neoPixel.emptyLedBuffer.length);
        assertEquals(10, neoPixel.lastLedBuffer.length);
    }

    @Test
    @DisplayName("initialize throws exception for invalid number of LEDs")
    void initializeThrowsExceptionForInvalidNumberOfLeds() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);

        assertThrows(IllegalArgumentException.class, () -> neoPixel.initialize(513, 3));
        assertThrows(IllegalArgumentException.class, () -> neoPixel.initialize(-1, 3));
    }

    @Test
    @DisplayName("initialize throws exception for invalid bytes per LED")
    void initializeThrowsExceptionForInvalidBytesPerLed() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);

        assertThrows(IllegalArgumentException.class, () -> neoPixel.initialize(10, 2));
        assertThrows(IllegalArgumentException.class, () -> neoPixel.initialize(10, 5));
    }

    @Test
    @DisplayName("setLeds sets colors correctly")
    void setLedsSetsColorsCorrectly() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        neoPixel.setLeds(0, colors);

        assertEquals(Color.RED, neoPixel.currentLedBuffer[0]);
        assertEquals(Color.GREEN, neoPixel.currentLedBuffer[1]);
        assertEquals(Color.BLUE, neoPixel.currentLedBuffer[2]);
    }

    @Test
    @DisplayName("setLeds throws exception for invalid index")
    void setLedsThrowsExceptionForInvalidIndex() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        assertThrows(IllegalArgumentException.class, () -> neoPixel.setLeds(-1, Color.RED));
        assertThrows(IllegalArgumentException.class, () -> neoPixel.setLeds(10, Color.RED));
    }

    @Test
    @DisplayName("setLeds throws exception for null or empty colors")
    void setLedsThrowsExceptionForNullOrEmptyColors() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        assertThrows(IllegalArgumentException.class, () -> neoPixel.setLeds(0, (Color[]) null));
        assertThrows(IllegalArgumentException.class, () -> neoPixel.setLeds(0, new Color[]{}));
    }

    @Test
    @DisplayName("clearLeds clears the buffer")
    void clearLedsClearsTheBuffer() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        neoPixel.setLeds(0, Color.RED);
        neoPixel.clearLeds();

        for (Color color : neoPixel.currentLedBuffer) {
            assertEquals(Color.BLACK, color);
        }
    }

    @Test
    @DisplayName("show updates the LEDs correctly")
    void showUpdatesTheLedsCorrectly() {
        I2cDeviceSynch mockDevice = mock(I2cDeviceSynch.class);
        AdafruitNeoPixel neoPixel = new AdafruitNeoPixel(mockDevice, true);
        neoPixel.initialize(10, 3);

        neoPixel.setLeds(0, Color.RED);
        neoPixel.show();

        verify(mockDevice, times(5)).write(any(byte[].class));
    }
}