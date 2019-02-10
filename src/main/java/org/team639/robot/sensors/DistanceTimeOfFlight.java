package org.team639.robot.sensors;

import edu.wpi.first.wpilibj.PWM;

/**
 * Wrapper for a PWM connection to an arduino reading a time of flight sensor.
 */
public class DistanceTimeOfFlight {

    public static final double CONVERSION_TO_INCHES = 2.39 * 0.0393701;

    private PWM pwm;

    /**
     * Creates a new distance sensor.
     * @param channel The PWM channel to read from.
     */
    public DistanceTimeOfFlight(int channel) {
        this.pwm = new PWM(channel);
    }

    /**
     * Returns the current distance registered in inches, up to about two feet.
     * @return the current distance registered in inches.
     */
    public double getDistance() {
        var raw = this.pwm.getRaw();
        return raw; // * CONVERSION_TO_INCHES;
    }
}
