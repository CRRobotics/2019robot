package org.team639.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

//Moves the climbing system pivot in the opposite direction with the Solenoid pistons.
public class PivotReverse extends Command {

    public void initialize()
    {
        super.initialize();
        Robot.getClimbing().getPiston1().set(false);
        Robot.getClimbing().getPiston2().set(false);
    }

    public boolean isFinished()
    {
        return true;
    }
}
