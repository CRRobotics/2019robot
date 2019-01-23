package org.team639.robot;

public class Constants {
    public static final boolean REAL = false;

    public static class Drivetrain {
        public static final double MIN_DRIVE_PERCENT = 0.09;

        public static final double SPEED_RANGE = 30000 * 0.95;
        public static final double DRIVE_P = 0.1;
        public static final double DRIVE_I = 0;
        public static final double DRIVE_D = 0;
        public static final double DRIVE_F = 1023 / SPEED_RANGE;

        public static final double WHEEL_DIAMETER_INCHES = 6;
        public static final double WHEEL_CIRCUMFERENCE_INCHES = Math.PI * WHEEL_DIAMETER_INCHES;

        public static final double ENC_TICKS_PER_ROTATION = 4096.0 * 3.0 * 54.0 / 30.0; // Quad encoder has 1024 ticks (* 4 = 4096). 3 and 54/30 are gear ratios.
        public static final double TICKS_PER_INCH = ENC_TICKS_PER_ROTATION / WHEEL_CIRCUMFERENCE_INCHES;
    }
}
