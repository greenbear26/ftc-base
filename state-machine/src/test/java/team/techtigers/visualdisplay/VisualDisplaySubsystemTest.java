package team.techtigers.visualdisplay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VisualDisplaySubsystemTest {
    @Test
    @DisplayName("Default view is automatically set to active view")
    void defaultViewIsAutomaticallySetToActiveView() {
        AdafruitNeoPixel mockDisplay = mock(AdafruitNeoPixel.class);
        VisualDisplaySubsystem visualDisplaySubsystem = new VisualDisplaySubsystem(mockDisplay, mock(DisplayView.class));
        assertEquals(visualDisplaySubsystem.activeView, visualDisplaySubsystem.views.get("default"));
    }

    @Test
    @DisplayName("addView adds view to views")
    void addViewAddsViewToViews() {
        AdafruitNeoPixel mockDisplay = mock(AdafruitNeoPixel.class);
        VisualDisplaySubsystem visualDisplay = new VisualDisplaySubsystem(mockDisplay, mock(DisplayView.class));
        DisplayView view = mock(DisplayView.class);
        visualDisplay.addView("test", view);
        assertEquals(view, visualDisplay.views.get("test"));
    }

    @Test
    @DisplayName("setView sets active view")
    void setViewSetsActiveView() {
        AdafruitNeoPixel mockDisplay = mock(AdafruitNeoPixel.class);
        VisualDisplaySubsystem visualDisplay = new VisualDisplaySubsystem(mockDisplay, mock(DisplayView.class));
        DisplayView view = mock(DisplayView.class);
        visualDisplay.addView("test", view);
        visualDisplay.setView("test");
        assertEquals(view, visualDisplay.activeView);
    }

    @Test
    @DisplayName("findLedArrayIndex returns correct index")
    void findLedArrayIndexReturnsCorrectIndex() {
        AdafruitNeoPixel mockDisplay = mock(AdafruitNeoPixel.class);
        VisualDisplaySubsystem visualDisplay = new VisualDisplaySubsystem(mockDisplay, mock(DisplayView.class));
        assertEquals(63, visualDisplay.findLedArrayIndex(0, 0));
        assertEquals(56, visualDisplay.findLedArrayIndex(0, 7));
        assertEquals(48, visualDisplay.findLedArrayIndex(1, 0));
        assertEquals(71, visualDisplay.findLedArrayIndex(8, 0));
        assertEquals(64, visualDisplay.findLedArrayIndex(8, 7));
        assertEquals(103, visualDisplay.findLedArrayIndex(12, 0));
        assertEquals(96, visualDisplay.findLedArrayIndex(12, 7));
        assertEquals(312, visualDisplay.findLedArrayIndex(39,0));
        assertEquals(319, visualDisplay.findLedArrayIndex(39,7));
        assertEquals(367, visualDisplay.findLedArrayIndex(42,0));
        assertEquals(360, visualDisplay.findLedArrayIndex(42,7));
    }

}
