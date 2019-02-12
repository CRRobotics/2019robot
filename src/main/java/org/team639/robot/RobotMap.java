package org.team639.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

/**
 * A central location for robot hardware.
 */
public class RobotMap {
    public static final TalonSRX leftDriveMaster = new TalonSRX(3);
    public static final TalonSRX rightDriveMaster = new TalonSRX(0);
    public static final AHRS navx = new AHRS(SPI.Port.kMXP);

    public static final TalonSRX lowerRollerExtension = new TalonSRX(6);
    public static final Spark lowerRoller = new Spark(0);
    public static final Spark upperRoller = new Spark(1);
    public static final Solenoid flowerOpen = new Solenoid(0);
    public static final Solenoid flowerForward = new Solenoid(1);

}
