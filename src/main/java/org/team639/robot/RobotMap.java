package org.team639.robot;

import edu.wpi.first.wpilibj.Solenoid;
import org.team639.lib.LoggingSolenoid;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class RobotMap {

    private static boolean initialized = false;

    private static Solenoid liftBrake;

    public static final TalonSRX leftDriveMaster = new TalonSRX(3);
    public static final TalonSRX rightDriveMaster = new TalonSRX(0);
    public static final AHRS navx = new AHRS(SPI.Port.kMXP);

    public static final TalonSRX liftMainTalon = new TalonSRX(6);
    public static final TalonSRX liftFollowerTalon = new TalonSRX(7);

    public static void init() {
        liftBrake = new LoggingSolenoid(6); // Not sure if the channel is correct, will probably change later
    }


    /*
     * Returns the Solenoid for operating the lift brake
     */
    public static Solenoid getLiftBrake() {
        return liftBrake;
    }
}
