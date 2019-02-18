package org.team639.robot;

import org.team639.lib.commands.ThreadedDriveCommand;
import org.team639.lib.controls.JoystickManager;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.commands.drive.FollowLine;
import org.team639.robot.commands.drive.SimpleArcFollower;

/**
 * Defines the mappings between buttons and actions and allows access to raw joystick values.
 */
public class OI {
    public static final JoystickManager drive = new XBoxController(0); // new DoubleLogitechAttack3(); // new LogitechF310(0);
    public static final JoystickManager controller = new XBoxController(1);

    public static void mapButtons() {
        controller.mapButton(XBoxController.Buttons.A, new ThreadedDriveCommand(new FollowLine(), Robot.drivetrain), JoystickManager.MappingType.WhenPressed);
    }
}
