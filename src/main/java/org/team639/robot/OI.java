package org.team639.robot;

import org.team639.lib.controls.JoystickManager;
import org.team639.lib.controls.XBoxController;
import org.team639.robot.commands.acqusition.*;

import static org.team639.robot.Constants.Acquisition.CARGO_EXPULSION_SPEED;

/**
 * Defines the mappings between buttons and actions and allows access to raw joystick values.
 */
public class OI {
    public static final JoystickManager drive = new XBoxController(0); // new DoubleLogitechAttack3(); // new LogitechF310(0);
    public static final JoystickManager controller = new XBoxController(1);

    public static void mapButtons() {
        controller.mapButton(XBoxController.Buttons.A, new ExtendFlower(), JoystickManager.MappingType.WhenPressed);
        controller.mapButton(XBoxController.Buttons.B, new RetractFlower(), JoystickManager.MappingType.WhenPressed);
        controller.mapButton(XBoxController.Buttons.X, new OpenFlower(), JoystickManager.MappingType.WhenPressed);
        controller.mapButton(XBoxController.Buttons.Y, new CloseFlower(), JoystickManager.MappingType.WhenPressed);

        controller.mapButton(XBoxController.Buttons.LB, new IntakeCargo(), JoystickManager.MappingType.WhenPressed);
        controller.mapButton(XBoxController.Buttons.RB, new SetIntakeSpeed(CARGO_EXPULSION_SPEED), JoystickManager.MappingType.WhenPressed);
        controller.mapButton(XBoxController.Buttons.RB, new SetIntakeSpeed(0), JoystickManager.MappingType.WhenReleased);
    }
}
