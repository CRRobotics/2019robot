package org.team639.robot;

import edu.wpi.first.wpilibj.Solenoid;
import org.team639.lib.LoggingSolenoid;

public class RobotMap {

    private static boolean initialized = false;

    private static Solenoid liftBrake;

    public static void init()
    {
        liftBrake = new LoggingSolenoid(6); // Not sure if the channel is correct, will probably change later
    }


    /*
     * Returns the Solenoid for operating the lift brake
     */
    public static Solenoid getLiftBrake()
    {
        return liftBrake;
    }
}
