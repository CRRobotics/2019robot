package org.team639.robot;

public class Constants {
    public static final int LIFT_MAX_HEIGHT = 8;        // Probably not real value, just temporary
    public static final int LIFT_MAX_SPEED = 1700;      // Probably not real value, just temporary
    public static final int LIFT_BRAKE_SPEED = 500;     // Temporary value, will be determined through testing
    public static final int LIFT_BALL_LOWER_HEIGHT = 0;
    public static final int LIFT_BALL_MIDDLE_HEIGHT = 0;
    public static final int LIFT_BALL_HIGH_HEIGHT = 0;
    public static final int LIFT_HATCH_LOWER_HEIGHT = 0;
    public static final int LIFT_HATCH_MIDDLE_HEIGHT = 0;
    public static final int LIFT_HATCH_HIGH_HEIGHT = 0;
    public static final int LIFT_CARGO_SHIP_HEIGHT = 0;

    //lift speed pid
    public static final double LIFT_P = 0;//0.05;
    public static final double LIFT_I = 0;//0.005;
    public static final double LIFT_D = 0;//10;
    public static final double LIFT_F = 1023.0 / LIFT_MAX_SPEED;

    // Unsure what these are, but MoveLiftToSetPosition needs them.
    public static final double LIFT_POS_P = 0.00036;
    public static final double LIFT_POS_I = 0;
    public static final double LIFT_POS_D = 0;

    // Unsure what the values for these should be
    public static final double LIFT_RATE = .03;
    public static final double LIFT_MIN = .25;
    public static final double LIFT_MAX = .8;

    // Unsure what this is, but MoveLiftToSetPosition needs it.
    public static final int LIFT_TOLERANCE = 600;

    // Constants for the MonitorLift command
    public static final double MOTOR_RADIUS = 1.0;
    public static final double LIFT_MASS = 1.0;

    // Arbitrary values for now, stuff for MotionMagic.
    public static final int LIFT_MOTION_MAGIC_CRUISING_SPEED = 1000;
    public static final int LIFT_MOTION_MAGIC_ACCELERATION = 1000;

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
