package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

import static org.team639.robot.Constants.LowerRoller.LOWER_SPEED;

/**
 * Extends the lower roller.
 */
public class RetractLowerRollerAsync extends Command {
    private Acquisition acquisition = Robot.acquisition;

    // Doesn't require Acquisition so other commands don't have to be interrupted
    public RetractLowerRollerAsync() {}

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        acquisition.setLowerRollerExtensionSpeed(-1 * LOWER_SPEED);
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
        return true;
    }
}
