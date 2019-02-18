package org.team639.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

import java.util.OptionalDouble;

/**
 * Class for interfacing with line follower modules through an analog input.
 */
public class LineFollower {
    private AnalogInput input;

    /**
     * Creates a LineFollower on the specified analog channel.
     * @param channel The analog channel the follower is connected to.
     */
    public LineFollower(int channel) {
        input = new AnalogInput(channel);
    }

    /**
     * Returns a value from -2.5 to 2.5 representing how far to the left or right of center the line is.
     * @return A value from -2.5 to 2.5 representing how far to the left or right of center the line is.
     */
    public OptionalDouble getRawPosition() {
        var val = input.getVoltage() - 2.5;
        if (Math.abs(val) > 2.3) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(val);
        }
    }
}
