package org.team639.robot.commands.climbing;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.OI;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.robot.Robot;
import org.team639.robot.subsystems.Climbing;

import static org.team639.robot.Constants.Acquisition.MINIMUM_EXTENSION_SPEED;
import static org.team639.robot.Constants.LIFT_JOYSTICK_DEADZONE;

/**
 * Moves the climbing lift based on joystick input.
 */
public class JoystickControlledClimb extends Command {

    private Climbing climbing = Robot.climbing;

    public JoystickControlledClimb() {
        requires(climbing);
    }

    public void execute() {

        if (climbing.isReleased()) {
            var val = OI.controller.getRightStickY();
//            System.out.println("aaah" + val + climbing.isFullyExtended());
            if (Math.abs(val) > LIFT_JOYSTICK_DEADZONE && !(climbing.isFullyExtended() && val < 0)) {
//                System.out.println("yes");
                climbing.setBrake(false);
                climbing.setSpeed(val);
            } else {
                climbing.setSpeed(0);
                climbing.setBrake(true);
            }

            var left = OI.controller.getControllerAxis(XBoxController.ControllerAxis.LeftTrigger);

            if (left < MINIMUM_EXTENSION_SPEED) {
                left = 0;
            }

            climbing.setPusherSpeed(-0.5 * left);
        } else {
//            System.out.println("no");
            climbing.setBrake(true);
        }
    }

    public boolean isFinished() {
        return false;
    }
}
