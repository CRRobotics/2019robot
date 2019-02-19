package org.team639.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class HatchDistanceSensor {
    private AnalogInput input;

    public HatchDistanceSensor(int channel) {
        this.input = new AnalogInput(channel);
    }

    public double getDistanceInches() {
        return input.getVoltage() * 1.5;
    }
}
