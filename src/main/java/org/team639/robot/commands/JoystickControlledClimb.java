package org.team639.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import org.team639.robot.subsystems.Climbing;

public class JoystickControlledClimb extends Command{

    Joystick joystick;
    Climbing climbing;

    //Ports are placeholders and will need to be replaced
    public JoystickControlledClimb()
    {
        joystick = new Joystick(0);
        climbing = new Climbing();
    }

    public void execute()
    {
        double input = joystick.getRawAxis(0);
        climbing.moveSystem(input);
    }

    public boolean isFinished()
    {
        return false;
    }
}
