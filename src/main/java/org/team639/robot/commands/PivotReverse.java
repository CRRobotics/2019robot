package org.team639.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

/*
 * Moves the climbing system pivot in the opposite direction with the Solenoid pistons.
 */
public class PivotReverse extends Command {

    public PivotReverse () {
        requires(Robot.getClimbing());
    }

    public void initialize()
    {
        super.initialize();
        Robot.getClimbing().setPistons(false);
    }

    public boolean isFinished()
    {
        return true;
    }
}
