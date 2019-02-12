package org.team639.robot;

/**
 * Contains constants for the robot.
 */
public class Constants {

    /**
     * Is this code running on the real robot?
     */
    public static final boolean REAL = false;

    /**
     * Constants for the drivetrain.
     */
    public static class Drivetrain {
        public static final double MIN_DRIVE_PERCENT = 0.09;

        public static final double SPEED_RANGE = 3300 * 0.95;
        public static final double DRIVE_P = 0.15;
        public static final double DRIVE_I = 0.000175;
        public static final double DRIVE_D = 0.75;
        public static final double DRIVE_F = 1023 / SPEED_RANGE;

        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double WHEEL_CIRCUMFERENCE_INCHES = Math.PI * WHEEL_DIAMETER_INCHES;

        public static final double ENC_TICKS_PER_ROTATION = 4096.0; // * 3.0 * 54.0 / 30.0; // Quad encoder has 1024 ticks (* 4 = 4096). 3 and 54/30 are gear ratios.
        public static final double TICKS_PER_INCH = ENC_TICKS_PER_ROTATION / WHEEL_CIRCUMFERENCE_INCHES;

        public static final double FPS_TO_MOTOR_UNITS = 1.0 / 10.0 * 12.0 * TICKS_PER_INCH;
        public static final double INCHES_PER_SECOND_TO_MOTOR_UNITS = 1.0 / 12 * FPS_TO_MOTOR_UNITS;

        public static final double TRACK_WIDTH_INCHES = 28;
        public static final double MAX_VELOCITY_INCHES_PER_SECOND = 6;
        public static final double K_VELOCITY = 1;
    }
}
