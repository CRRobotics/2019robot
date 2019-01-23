package org.team639.lib.controls;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public abstract class JoystickManager {

    public enum MappingType {
        WhenPressed,
        WhileHeld,
        WhenReleased,
        ToggleWhenPressed,
        CancelWhenPressed
    }

    protected interface ButtonType {}

    protected interface ControllerAxisType {}

    /**
     * Returns the Y axis value of the left drive Joystick with forward being 1 and backward being -1
     * @return The Y axis value of the left drive Joystick
     */
    public abstract double getLeftStickY();

    /**
     * Returns the Y axis value of the right drive Joystick with forward being 1 and backward being -1
     * @return The Y axis value of the right drive Joystick
     */
    public abstract double getRightStickY();

    /**
     * Returns the X axis value of the left driveJoystick with to the right being 1 and to the left being -1
     * @return The X axis value of the left drive Joystick
     */
    public abstract double getLeftStickX();

    /**
     * Returns the X axis value of the left drive Joystick with to the right being 1 and to the left being -1
     * @return The X axis value of the left drive Joystick
     */
    public abstract double getRightStickX();

    /**
     * Maps the specified command to the specified button
     * @param btn The location of the button
     * @param cmd The command to map
     * @param type The type of mapping
     */
    public abstract void mapButton(ButtonType btn, Command cmd, MappingType type);

    /**
     * Returns whether a button is pressed
     * @param btn The button to retrieve
     * @return Whether or not the button is pressed.
     */
    public abstract boolean getButtonPressed(ButtonType btn);

    /**
     * Returns the value from the specified controller axis from -1 to 1
     * @param axis The controller axis to return
     * @return the value from the specified controller axis
     */
    public abstract double getControllerAxis(ControllerAxisType axis);

    /**
     * Returns the angle of the left drive joystick, with 90 degrees being straight ahead.
     * @return The angle of the left drive joystick
     */
    public double getLeftDriveAngle() {
        double x = getLeftStickX();
        double y = getLeftStickY();
        double angle = Math.atan2(y,x);
        angle = Math.toDegrees(angle);
        return angle;
    }

    /**
     * Returns the angle of the right drive joystick, with 90 degrees being straight ahead.
     * @return The angle of the right drive joystick
     */
    public double getRightDriveAngle() {
        double x = getRightStickX();
        double y = getRightStickY();
        double angle = Math.atan2(y,x);
        angle = Math.toDegrees(angle);
        if (angle < 0) angle = 360 + angle;
        return angle;
    }

    public abstract void setRightRumble(double rumble);

    public abstract void setLeftRumble(double rumble);

    public abstract void setAllRumble(double rumble);
}