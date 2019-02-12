package org.team639.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

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
    public double getRawPosition() {
        return input.getVoltage() - 2.5;
    }
}
