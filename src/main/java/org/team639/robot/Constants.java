package org.team639.robot;

public class Constants {
    public static final int LIFT_MAX_HEIGHT = 8;        // Probably not real value, just temporary
    public static final int LIFT_MAX_SPEED = 1700;      // Probably not real value, just temporary
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

    // Unsure what this is, but MoveLiiftToSetPosition needs it.
    public static final int LIFT_TOLERANCE = 600;
}
