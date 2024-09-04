package team.techtigers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;

import team.techtigers.core.utils.GlobalState;
import team.techtigers.statemachine.CloseableSubsytem;


/**
 * A CommandOpMode that allows for custom telemetry and more features
 */
public abstract class BaseOpMode extends CommandOpMode {
    protected GlobalState robotState;
    private CloseableSubsytem[] subsystems;

    /**
     * Method where telemetry is set. All telemetry messages should be set here.
     */
    protected void setTelemetry() {
    }

    /**
     * Method that is called just after the OpMode starts. It is recommended to
     * use subsystems init() method, but this is also an option.
     */
    protected void justAfterStart() {
    }

    /**
     * Method that is called as the OpMode ends. It is recommended to
     * use subsystems end() method, but this is also an option.
     */
    protected void end() {
    }

    /**
     * Child classes must invoke this method to ensure that the subsystems are properly registered
     * and cleaned up after the OpMode is finished.
     *
     * @param subsystems The subsystems to register
     */
    protected void registerSubsystems(CloseableSubsytem... subsystems) {
        this.subsystems = subsystems;
        super.register(subsystems);
    }

    @Override
    public void register(Subsystem... subsystems) {
        throw new UnsupportedOperationException("Use registerSubsystems() instead of register()");
    }

    @Override
    public void runOpMode() {
        robotState = new GlobalState();
        subsystems = new CloseableSubsytem[0];

        try {
            initialize();
            waitForStart();
            for (CloseableSubsytem subsystem : subsystems) {
                subsystem.init();
            }
            justAfterStart();

            // run the scheduler
            while (!isStopRequested() && opModeIsActive()) {
                run();
                setTelemetry();
                telemetry.update();
            }
        } finally {
            reset();
            // Cleaning up after execution, whether or not there are no errors
            for (CloseableSubsytem subsystem : subsystems) {
                subsystem.close();
            }
            end();
        }
    }
}