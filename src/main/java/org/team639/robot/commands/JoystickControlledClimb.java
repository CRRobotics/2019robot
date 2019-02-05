package org.team639.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;

/*
 * Moves the motors of the climbing system based on joystick values.
 */
public class JoystickControlledClimb extends Command{

    public JoystickControlledClimb () {
        requires(Robot.getClimbing());
    }

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
