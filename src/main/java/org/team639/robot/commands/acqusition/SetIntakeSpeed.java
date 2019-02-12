package org.team639.robot.commands.acqusition;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Acquisition;

/**
 * Sets the speed of the cargo intake rollers
 */
public class SetIntakeSpeed extends Command {

    private Acquisition acquisition = Robot.acquisition;
    private double speed;

    public SetIntakeSpeed(double speed) {
        requires(acquisition);
        this.speed = speed;
    }

    /**
     * The initialize method is called the first time this Command is run after being started.
     */
    @Override
    protected void initialize() {
        acquisition.setUpperRollerSpeed(this.speed);
        acquisition.setLowerRollerSpeed(this.speed);
    }

    /**
     * Returns whether this command is finished. If it is, then the command will be removed and {@link
     * Command#end() end()} will be called.
     *
     * <p>It may be useful for a team to reference the {@link Command#isTimedOut() isTimedOut()}
     * method for time-sensitive commands.
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        return true;
    }
}
