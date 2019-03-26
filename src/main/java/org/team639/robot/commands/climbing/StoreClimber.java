package org.team639.robot.commands.climbing;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

/**
 * Moves the climbing system to its stored position. (In theory. We might not have the pressure to actually do this
 * but there's code for it anyway)
 */
public class StoreClimber extends Command {

    public StoreClimber() {
        requires(Robot.climbing);
    }

    public void initialize() {
        Robot.climbing.setReleased(false);
    }

    public boolean isFinished()
    {
        return true;
    }
}
