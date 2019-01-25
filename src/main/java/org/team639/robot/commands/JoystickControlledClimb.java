package org.team639.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

public class JoystickControlledClimb extends Command{

    public void execute()
    {
        double input = Robot.getJoystick().getRawAxis(0);
        Robot.getClimbing().moveSystem(input);
    }

    public boolean isFinished()
    {
        return false;
    }
}
