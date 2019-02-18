package org.team639.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import org.team639.lib.commands.ThreadedDriveCommand;
import org.team639.lib.controls.JoystickManager;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.commands.lift.*;
import org.team639.robot.commands.drive.FollowLine;

import static org.team639.robot.Constants.LIFT_JOYSTICK_DEADZONE;

/**
 * Defines the mappings between buttons and actions and allows access to raw joystick values.
 */
public class OI {
    public static final JoystickManager drive = new XBoxController(0); // new DoubleLogitechAttack3(); // new LogitechF310(0);
    public static final JoystickManager controller = new XBoxController(1);

    public static void mapButtons() {

//        controller.mapButton(XBoxController.Buttons.A, new ThreadedDriveCommand(new FollowLine(), Robot.drivetrain), JoystickManager.MappingType.WhenPressed);

//        controller.mapButton(XBoxController.Buttons.A, new ThreadedDriveCommand(new SquiggleFollower(), Robot.drivetrain), JoystickManager.MappingType.WhenPressed);
        Button liftJoystickActivated = new Button() {
            @Override
            public boolean get() {
                return Math.abs(controller.getRightStickY()) > LIFT_JOYSTICK_DEADZONE;
            }
        };
        mapCondition(liftJoystickActivated, new MoveLiftWithJoystick(), JoystickManager.MappingType.WhenPressed);

        // Up -> Top
        // Right -> Middle
        // Down -> Bottom
        // Left -> Cargo Hatch
//        controller.mapButton(XBoxController.Buttons.POVUp, new MoveToTop(), JoystickManager.MappingType.WhenPressed);
//        controller.mapButton(XBoxController.Buttons.POVRight, new MoveToMiddle(), JoystickManager.MappingType.WhenPressed);
//        controller.mapButton(XBoxController.Buttons.POVDown, new MoveToBottom(), JoystickManager.MappingType.WhenPressed);
//        controller.mapButton(XBoxController.Buttons.POVLeft, new MoveLiftToSetPosition(LiftPosition.CARGO_SHIP), JoystickManager.MappingType.WhenPressed);
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
