package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

/**
 * Retracts the hatch acquisition.
 */
public class RetractFlower extends Command {
    private Acquisition acquisition = Robot.acquisition;

    public RetractFlower() {
        requires(acquisition);
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        if (!acquisition.isHatchOnFlower()) acquisition.setFlowerForward(false);
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
