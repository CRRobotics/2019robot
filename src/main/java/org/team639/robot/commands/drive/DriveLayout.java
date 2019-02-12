package org.team639.robot.commands.drive;

/**
 * The possible joystick configurations that can be used to drive the robot.
 */
public enum DriveLayout {
    /**
     * Direct mapping between the y-axes of two joysticks and the speeds of each side of the drivetrain.
     */
    Tank,

    /**
     * Forward/backward and turning all on one joystick.
     */
    Arcade1JoystickRight,

    /**
     * Forward/backward on the left joystick and turning on the right.
     */
    Arcade2JoystickLeft,

    /**
     * Forward/backward on the right joystick and turning on the left.
     */
    Arcade2JoystickRight,
}
