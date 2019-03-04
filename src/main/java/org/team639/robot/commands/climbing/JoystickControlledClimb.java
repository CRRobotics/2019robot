package org.team639.robot.commands.climbing;
import org.team639.robot.OI;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Climbing;

import static org.team639.robot.Constants.LIFT_JOYSTICK_DEADZONE;

/**
 * Moves the climbing lift based on joystick input.
 */
public class JoystickControlledClimb extends Command {

    private Climbing climbing = Robot.climbing;

    public JoystickControlledClimb () {
        requires(climbing);
    }

    public void execute() {
        if (climbing.isReleased()) {
            var val = OI.controller.getRightStickY();
            if (Math.abs(val) > LIFT_JOYSTICK_DEADZONE) {
                climbing.setBrake(false);
                climbing.setSpeed(OI.controller.getRightStickY());
            } else {
                climbing.setSpeed(0);
                climbing.setBrake(true);
            }
        } else {
            climbing.setBrake(true);
        }
    }

    public boolean isFinished() {
        return false;
    }
}
