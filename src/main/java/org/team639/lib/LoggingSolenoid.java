package org.team639.lib;

import edu.wpi.first.wpilibj.Solenoid;

public class LoggingSolenoid extends Solenoid {
    private int ch;

    /**
     * Constructor using the default PCM ID (defaults to 0).
     *
     * @param channel The channel on the PCM to control (0..7).
     */
    public LoggingSolenoid(int channel) {
        super(channel);
        ch = channel;
    }

    /**
     * Constructor.
     *
     * @param moduleNumber The CAN ID of the PCM the solenoid is attached to.
     * @param channel      The channel on the PCM to control (0..7).
     */
    public LoggingSolenoid(int moduleNumber, int channel) {
        super(moduleNumber, channel);

        ch = channel;
    }

    /**
     * Set the value of a solenoid.
     *
     * @param on True will turn the solenoid output on. False will turn the solenoid output off.
     */
    @Override
    public void set(boolean on) {
        super.set(on);

        if (on != get()) System.out.println("Setting solenoid " + ch + " to " + on);
    }
}