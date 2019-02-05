package org.team639.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

/*
 * Moves the climbing system pivot with the Solenoid pistons.
 */
public class Pivot extends Command {

    public Pivot () {
        requires(Robot.getClimbing());
    }

    @Override
    public void initialize()
    {
        super.initialize();
        Robot.getClimbing().setPistons(true);
    }

    public boolean isFinished()
    {
        return true;
    }
}
