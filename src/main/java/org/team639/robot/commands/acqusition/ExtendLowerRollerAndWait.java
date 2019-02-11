package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.math.PID;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

import static org.team639.robot.Constants.AuxiliaryRoller.*;

/**
 * Extends the lower roller.
 */
public class ExtendLowerRollerAndWait extends Command {
    private Acquisition acquisition = Robot.acquisition;
    private PID pid;

    // Doesn't require Acquisition so other commands don't have to be interrupted
    public ExtendLowerRollerAndWait() {}

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        super.initialize();
        pid = new PID(P, I, D, MIN_SPEED, MAX_SPEED, RATE, TOLERANCE, I_CAP);
    }

    /**
     * The execute method is called repeatedly until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        var output = pid.compute(LOWERED_TICKS - acquisition.getLowerRollerExtensionEncoderPosition());
        acquisition.setLowerRollerExtensionSpeed(output);
    }

    /**
     * Called when the command ended peacefully. This is where you may want to wrap up loose ends,
     * like shutting off a motor that was being used in the command.
     */
    @Override
    protected void end() {
        acquisition.setLowerRollerExtensionSpeed(0);
    }

    /**
     * Called when the command ends because somebody called {@link Command#cancel() cancel()} or
     * another command shared the same requirements as this one, and booted it out.
     *
     * <p>This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     *
     * <p>Generally, it is useful to simply call the {@link Command#end() end()} method within this
     * method, as done here.
     */
    @Override
    protected void interrupted() {
        end();
    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     * for this.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return acquisition.isLowerRollerExtended();
    }
}
