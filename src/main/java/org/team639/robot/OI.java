package org.team639.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.commands.ThreadedDriveCommand;
import org.team639.lib.controls.JoystickManager;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.commands.drive.SquiggleFollower;
import org.team639.robot.commands.lift.MoveLiftWithJoystick;

/**
 * Defines the mappings between buttons and actions and allows access to raw joystick values.
 */
public class OI {
    public static final JoystickManager drive = new XBoxController(0); // new DoubleLogitechAttack3(); // new LogitechF310(0);
    public static final JoystickManager controller = new XBoxController(1);

    public static void mapButtons() {
//        controller.mapButton(XBoxController.Buttons.A, new ThreadedDriveCommand(new SquiggleFollower(), Robot.drivetrain), JoystickManager.MappingType.WhenPressed);
        Button liftJoystickActivated = new Button() {
            @Override
            public boolean get() {
                return Math.abs(controller.getRightStickY()) > 0;
            }
        };
        mapCondition(liftJoystickActivated, new MoveLiftWithJoystick(controller), JoystickManager.MappingType.WhenPressed);
    }

    public static void mapCondition(Button condition, Command cmd, JoystickManager.MappingType type) {
        switch (type) {
            case WhenPressed:
                condition.whenPressed(cmd);
                break;
            case WhenReleased:
                condition.whenReleased(cmd);
                break;
            case WhileHeld:
                condition.whileHeld(cmd);
                break;
            case CancelWhenPressed:
                condition.cancelWhenPressed(cmd);
                break;
            case ToggleWhenPressed:
                condition.toggleWhenPressed(cmd);
                break;
        }
    }
}
