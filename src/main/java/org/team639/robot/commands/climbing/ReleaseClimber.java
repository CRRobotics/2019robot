package org.team639.robot.commands.climbing;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

/**
 * Releases the climbing system.
 */
public class ReleaseClimber extends Command {

    public ReleaseClimber() {
        requires(Robot.climbing);
    }

    @Override
    public void initialize() {
        Robot.climbing.setReleased(true);
    }

    public boolean isFinished()
    {
        return true;
    }
}
