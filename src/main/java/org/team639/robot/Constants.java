package org.team639.robot;

public class Constants {

    // All need to be determined through testing.
    public static final int LIFT_MAX_HEIGHT = 8;
    public static final int LIFT_MAX_SPEED = 1700;
    public static final int LIFT_BRAKE_SPEED = 500;
    public static final int LIFT_BALL_LOWER_HEIGHT = 0;
    public static final int LIFT_BALL_MIDDLE_HEIGHT = 0;
    public static final int LIFT_BALL_HIGH_HEIGHT = 0;
    public static final int LIFT_HATCH_LOWER_HEIGHT = 0;
    public static final int LIFT_HATCH_MIDDLE_HEIGHT = 0;
    public static final int LIFT_HATCH_HIGH_HEIGHT = 0;
    public static final int LIFT_CARGO_SHIP_HEIGHT = 0;

    public static final double LIFT_ZERO_SPEED = 0.1;

    // Lift Speed PID
    public static final double LIFT_P = 0;//0.05;
    public static final double LIFT_I = 0;//0.005;
    public static final double LIFT_D = 0;//10;
    public static final double LIFT_F = 1023.0 / LIFT_MAX_SPEED;

    // Lift Position PID
    public static final double LIFT_POS_P = 0.00036;
    public static final double LIFT_POS_I = 0;
    public static final double LIFT_POS_D = 0;

    // All need to be determined through testing.
    public static final double LIFT_RATE = .03;
    public static final double LIFT_MIN = .25;
    public static final double LIFT_MAX = .8;

    // Needs to be determined through testing.
    public static final int LIFT_TOLERANCE = 600;

    public static final double LIFT_JOYSTICK_DEADZONE = 0.1;

    public static final double LIFT_BRAKE_ERROR = 1000;

    /**
     * Is this code running on the real robot?
     */
    public static final boolean REAL = true;

    /**
     * Constants for the drivetrain.
     */
    public static class Drivetrain {
        public static final double RAMP = 0.25;

        public static final double MIN_DRIVE_PERCENT = 0.3;

        public static final double SPEED_RANGE = (REAL ? 2200 : 3300) * 0.95;
        public static final double DRIVE_P = 0.1;
        public static final double DRIVE_I = 0.0002;
        public static final double DRIVE_D = 0.02;
        public static final double DRIVE_F = 1023 / SPEED_RANGE;

        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double WHEEL_CIRCUMFERENCE_INCHES = Math.PI * WHEEL_DIAMETER_INCHES;

        public static final double ENC_TICKS_PER_ROTATION = 4096.0; // * 3.0 * 54.0 / 30.0; // Quad encoder has 1024 ticks (* 4 = 4096). 3 and 54/30 are gear ratios.
        public static final double TICKS_PER_INCH = ENC_TICKS_PER_ROTATION / WHEEL_CIRCUMFERENCE_INCHES;

        public static final double FPS_TO_MOTOR_UNITS = 1.0 / 10.0 * 12.0 * TICKS_PER_INCH;
        public static final double INCHES_PER_SECOND_TO_MOTOR_UNITS = 1.0 / 12 * FPS_TO_MOTOR_UNITS;

        public static final double TRACK_WIDTH_INCHES = 34.3;
        public static final double MAX_VELOCITY_INCHES_PER_SECOND = 6;
        public static final double K_VELOCITY = 1;

        public static final double AC_P = 0.01;
        public static final double AC_I = 0.00000001;
        public static final double AC_D = 0;
        public static final double AC_MIN = 0;
        public static final double AC_MAX = 0.03;
        public static final double AC_RATE = 0.01;
        public static final double AC_I_CAP = 0.2;
        public static final double AC_TOLERANCE = 1;

        public static final double VISION_APPROACH_BASE_SPEED = 0.3;
    }

    public static class LowerRoller {
        public static final double P = 0.1;
        public static final double I = 0;
        public static final double D = 0;
        public static final double MAX_SPEED = 1;
        public static final double MIN_SPEED = 0.2;
        public static final double RATE = 0.3;
        public static final double TOLERANCE = 0;
        public static final double I_CAP = 5;
        public static final double LOWERED_TICKS = 2000;
        public static final double LOWER_SPEED = 0.5;
    }

    public static class Acquisition {
        public static final double CARGO_INTAKE_SPEED = .85;
        public static final double CARGO_INTAKE_STALL = 0.15;

        public static final double HATCH_OPEN_DELAY = 0.2;
        public static final double HATCH_EXTEND_DELAY = 0.2;

        public static final double CARGO_EXPULSION_SPEED = -0.8;

        public static final double HATCH_DETECT_DISTANCE = 3.0;

        public static final double MINIMUM_EXTENSION_SPEED = 0.1;
    }

    /**
     * Constants for the drivetrain.
     */
    public static class Climbing {
        //Encoder variables for climbing
        public static final double ENC_TICKS_PER_ROTATION_CLIMBING = 1; //I don't actually know
        public static final double WHEEL_CIRCUMFERENCE_CLIMBING = 1; //I don't actually know
        public static final double TICKS_PER_INCH_CLIMBING = ENC_TICKS_PER_ROTATION_CLIMBING / WHEEL_CIRCUMFERENCE_CLIMBING;

    }
}
