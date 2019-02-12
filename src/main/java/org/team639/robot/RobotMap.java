package org.team639.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

/**
 * A central location for robot hardware.
 */
public class RobotMap {
    public static final TalonSRX leftDriveMaster = new TalonSRX(3);
    public static final TalonSRX rightDriveMaster = new TalonSRX(0);
    public static final AHRS navx = new AHRS(SPI.Port.kMXP);
}
