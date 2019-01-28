package org.team639.robot;

public class Constants {
    public static final double LIFT_MAX_HEIGHT = 8.0f;

    public enum liftPosition
    {

        // Height in feet from the bottom of the lift
        // Heights to be implemented
        TOP_BALL(0.0),
        TOP_HATCH(0.0),
        MIDDLE_BALL(0.0),
        MIDDLE_HATCH(0.0),
        BOTTOM_BALL(0.0),
        BOTTOM_HATCH(0.0),
        CARGO_SHIP(0.0);

        double position;

        liftPosition(double position)
        {
            this.position = position;
        }
    }
}
