package team.techtigers.statemachine;

import org.firstinspires.ftc.teamcode.display.region.DisplayRegion;

import java.util.HashMap;

/**
 * Output subsystem that controls the visual feedback display
 */
public class VisualDisplay extends YetAnotherSubsystemBase {
    private final AdafruitNeoPixel visualDisplay;
    private final HashMap<String, DisplayView> views;
    private DisplayView activeView;

    /**
     * Initializes a new visual feedback subsystem object. Obtains references to visual feedback
     *
     * @param defaultView the default view to display
     */
    public VisualDisplay(AdafruitNeoPixel visualDisplay, DisplayView defaultView) {
        this.visualDisplay = visualDisplay;
        visualDisplay.initialize(384, 3);
        views = new HashMap<>();
        views.put("default", defaultView);
        activeView = defaultView;
    }

    /**
     * Adds a view to the list of views
     *
     * @param key  the key to access the view
     * @param view the view to add
     */
    public void addView(String key, DisplayView view) {
        views.put(key, view);
    }

    /**
     * Sets the active view
     *
     * @param key the key to access the view
     */
    public void setView(String key) {
        activeView = views.get(key);
    }

    private int findLedArrayIndex(int ledX, int ledY) {
        if (ledX < 8) {
            ledX = 7 - ledX;
        } else if (ledX > 39) {
            ledX = ledX - 40;
            ledX = 7 - ledX;
            ledX = ledX + 40;
        } else {
            ledY = 7 - ledY;
        }

        if (ledX % 2 == 0) {
            return ledX * 8 + ledY;
        } else {
            return ledX * 8 + 7 - ledY;
        }
    }

    /**
     * Updates the color of the LEDs based on the current state of the robot
     */
    @Override
    public void periodic() {
        visualDisplay.clearLeds();
        for (DisplayRegion region : activeView.getRegions()) {
            region.update();
        }

        for (DisplayRegion region : activeView.getRegions()) {
            Color[][] colors = region.render();
            for (int y = 0; y < region.getHeight(); y++) {
                for (int x = 0; x < region.getWidth(); x++) {
                    if (colors[x][y] == null) {
                        throw new RuntimeException("color is null:" + x + " " + y);
                    }
                    int ledIndex = findLedArrayIndex(region.getX() + x, region.getY() + y);
                    visualDisplay.setLeds(ledIndex, colors[x][y]);
                }
            }
        }
        visualDisplay.show();
    }
}
