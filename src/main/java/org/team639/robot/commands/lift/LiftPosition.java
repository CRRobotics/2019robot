package org.team639.robot.commands.lift;

import static org.team639.robot.Constants.*;

/**
 * The possible preset positions that the lift can travel to.
 */
public enum LiftPosition {

    // Height in feet from the bottom of the lift
    // Heights to be implemented
    TOP_BALL(LIFT_BALL_HIGH_HEIGHT),
    TOP_HATCH(LIFT_HATCH_HIGH_HEIGHT),
    MIDDLE_BALL(LIFT_BALL_MIDDLE_HEIGHT),
    MIDDLE_HATCH(LIFT_HATCH_MIDDLE_HEIGHT),
    BOTTOM_BALL(LIFT_BALL_LOWER_HEIGHT),
    BOTTOM_HATCH(LIFT_HATCH_LOWER_HEIGHT),
    CARGO_SHIP(LIFT_CARGO_SHIP_HEIGHT);

    private final int encTicks;

    LiftPosition(int encTicks) {
        this.encTicks = encTicks;
    }

    /**
     * Returns the number of encoder ticks of the position.
     *
     * @return The number of encoder ticks of the position.
     */
    public int getEncTicks() {
        return encTicks;
    }
}